package eng.la.persistence;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import eng.la.model.Articolo;
import eng.la.model.CategoriaMailinglist;
import eng.la.model.Documento;
import eng.la.model.DocumentoArticolo;
import eng.la.model.Newsletter;
import eng.la.model.RNewsletterArticolo;
import eng.la.model.custom.NewsArticlePageHome;
import eng.la.model.custom.NewsletterArticleCustom;
import eng.la.model.custom.NewsletterArticleListCustom;
import eng.la.model.custom.NewsletterCustom;

@Component("newsDao")
public class NewsDaoImpl extends HibernateDaoSupport implements NewsDao, CostantiDAO {

	@Autowired
	public NewsDaoImpl(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	public CategoriaMailinglist getCategoria(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( CategoriaMailinglist.class ).add( Restrictions.eq("id", id) );
		@SuppressWarnings("unchecked")
		CategoriaMailinglist vo = (CategoriaMailinglist) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
		return vo; 
	}
	
	public List<CategoriaMailinglist> getCategoriaPadre(String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( CategoriaMailinglist.class ).addOrder(Order.asc("ord"));
		criteria.add(Restrictions.isNull("categoriaPadre"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.add(Restrictions.eq("lang", lingua));
		
		@SuppressWarnings("unchecked")
		List<CategoriaMailinglist> lista = getHibernateTemplate().findByCriteria(criteria);		
		return lista; 
		 
	}
	
	
	public List<CategoriaMailinglist> getSottoCategoria(long idCategoria ,String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( CategoriaMailinglist.class ).addOrder(Order.asc("ord"));
		@SuppressWarnings("unchecked")
		CategoriaMailinglist vo = (CategoriaMailinglist) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(DetachedCriteria.forClass( CategoriaMailinglist.class ).add( Restrictions.eq("id", idCategoria) )) );
		criteria.add(Restrictions.eq("categoriaPadre",vo));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.add(Restrictions.eq("lang", lingua));
		@SuppressWarnings("unchecked")
		List<CategoriaMailinglist> lista = getHibernateTemplate().findByCriteria(criteria);		
		return lista; 
		 
	}
	
	public NewsletterArticleListCustom getArticleFromNewsletterId(long id,int elementiPerPagina, int paginaNumenro) throws Throwable{
		 
		NewsletterArticleCustom articleCustom=new NewsletterArticleCustom();
		@SuppressWarnings("unchecked")
		Newsletter newsletter = (Newsletter) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(DetachedCriteria.forClass( Newsletter.class ).add( Restrictions.eq("id", id) )) );
		articleCustom.setNewsletter(newsletter);
		
		Long totaleElementi = contaArticle(id,newsletter);//conta("");
		
		int indiceStrat = elementiPerPagina * (paginaNumenro - 1);
		if (totaleElementi.intValue() < indiceStrat) {
			indiceStrat = 0;
		}
		
		DetachedCriteria criteria = DetachedCriteria.forClass(RNewsletterArticolo.class)
				.addOrder(Order.desc("id")); 
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.add(Restrictions.eq("newsletter",newsletter));
		
		@SuppressWarnings("unchecked")
		List<RNewsletterArticolo> rNewsletterArticolos =(List<RNewsletterArticolo>)getHibernateTemplate().findByCriteria(criteria, indiceStrat, indiceStrat+elementiPerPagina);
		
		List<Articolo> articolos= new ArrayList<Articolo>();
		
		for(RNewsletterArticolo r:rNewsletterArticolos){
			articolos.add(r.getArticolo());
		}
		
		 articleCustom.setArticolos(articolos);
		
		 List<NewsletterArticleCustom> newsletterArticleCustomsList=new ArrayList<>();
		 newsletterArticleCustomsList.add(articleCustom);
	
		return new NewsletterArticleListCustom(totaleElementi.intValue(),newsletterArticleCustomsList); 
		}
	
	
	public NewsletterCustom getAllNewsLetter(String titolo,int anno,int elementiPerPagina, int paginaNumenro) throws Throwable{
		Long totaleElementi = contaAllNewsLetter(titolo, anno);
		
		if(anno==0)
		{
			anno = Calendar.getInstance().get(Calendar.YEAR);
		}
		String dataStartString="01/01/"+anno;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date dateStart= dateFormat.parse(dataStartString);
		
		String dataEndString="31/12/"+anno;
		SimpleDateFormat dateFormatEnd = new SimpleDateFormat("dd/MM/yyyy");
		Date dateEnd= dateFormatEnd.parse(dataEndString);
		
		
		DetachedCriteria criteria = DetachedCriteria.forClass(Newsletter.class)
				.addOrder(Order.desc("dataCreazione"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		
		// se non arrivo dal Search -> aziono il filtro per anno
		if(titolo==null || titolo.trim().equals("")) 
		criteria.add(Restrictions.between("dataCreazione", dateStart,dateEnd));
		 
		if(titolo!=null && !titolo.trim().equals(""))
		criteria.add(Restrictions.like("titolo", titolo,MatchMode.ANYWHERE));	
		
		criteria.createAlias("stato", "stato");
		criteria.add(Restrictions.eq("stato.codGruppoLingua", "I"));
		criteria.add(Restrictions.eq("stato.lang", "IT")); 
		int indiceStrat = elementiPerPagina * (paginaNumenro - 1);
		if (totaleElementi.intValue() < indiceStrat) {
			indiceStrat = 0;
		}
		
		@SuppressWarnings("unchecked")
		List<Newsletter> listAll =(List<Newsletter>)getHibernateTemplate().findByCriteria(criteria, indiceStrat, indiceStrat+elementiPerPagina);
		
		return new NewsletterCustom(totaleElementi.intValue(),listAll); 
		}
	
	
	public Long contaAllNewsLetter(String titolo,int anno) throws Throwable {
		if(anno==0)
		{
			anno = Calendar.getInstance().get(Calendar.YEAR);
		}
		String dataStartString="01/01/"+anno;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date dateStart= dateFormat.parse(dataStartString);
		
		String dataEndString="31/12/"+anno;
		SimpleDateFormat dateFormatEnd = new SimpleDateFormat("dd/MM/yyyy");
		Date dateEnd= dateFormatEnd.parse(dataEndString);
		
		
		DetachedCriteria criteria = DetachedCriteria.forClass(Newsletter.class)
				.addOrder(Order.desc("dataCreazione"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		
		// se non arrivo dal Search -> aziono il filtro per anno
		if(titolo==null || titolo.trim().equals("")) 
		criteria.add(Restrictions.between("dataCreazione", dateStart,dateEnd));
		 
		if(titolo!=null && !titolo.trim().equals(""))
		criteria.add(Restrictions.like("titolo", titolo,MatchMode.ANYWHERE));	
		
		criteria.createAlias("stato", "stato");
		
		criteria.add(Restrictions.eq("stato.codGruppoLingua", "I")); 
		criteria.add(Restrictions.eq("stato.lang", "IT"));

		criteria.setProjection(Projections.distinct(Projections.countDistinct("id")));
		@SuppressWarnings("unchecked")
		Long conta = (Long) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return conta;
	   
	} 
	
	public Long conta(String titolo) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Newsletter.class);
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.createAlias("stato", "stato");
		criteria.add(Restrictions.eq("stato.codGruppoLingua", "I"));
		criteria.add(Restrictions.eq("stato.lang", "IT"));
		if( titolo != null && titolo.trim().length() > 0 ){
			criteria.add( Restrictions.ilike("titolo", titolo, MatchMode.ANYWHERE) ) ;
		} 

		criteria.setProjection(Projections.distinct(Projections.countDistinct("id")));
		@SuppressWarnings("unchecked")
		Long conta = (Long) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return conta;
	   
	} 
	
	public Long contaArticle(long id,Newsletter newsletter) throws Throwable {
		 
		//Newsletter newsletter = (Newsletter) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(DetachedCriteria.forClass( Newsletter.class ).add( Restrictions.eq("id", id) )) );
		
		DetachedCriteria criteria = DetachedCriteria.forClass(RNewsletterArticolo.class)
				.addOrder(Order.desc("id")); 
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.add(Restrictions.eq("newsletter",newsletter));

		criteria.setProjection(Projections.distinct(Projections.countDistinct("id")));
		@SuppressWarnings("unchecked")
		Long conta = (Long) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return conta;
	   
	}
	
	public Long contaNewsLetterFromCategory(long idCategory,int anno) throws Throwable {
		if(anno==0)
		{
			anno = Calendar.getInstance().get(Calendar.YEAR);
		}
		String dataStartString="01/01/"+anno;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date dateStart= dateFormat.parse(dataStartString);
		
		String dataEndString="31/12/"+anno;
		SimpleDateFormat dateFormatEnd = new SimpleDateFormat("dd/MM/yyyy");
		Date dateEnd= dateFormatEnd.parse(dataEndString);
		
		
		@SuppressWarnings("unchecked")
		CategoriaMailinglist categoriaMailinglist = (CategoriaMailinglist) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(DetachedCriteria.forClass( CategoriaMailinglist.class ).add( Restrictions.eq("id", idCategory) )) );
		DetachedCriteria criteria = DetachedCriteria.forClass(Newsletter.class)
				.addOrder(Order.desc("dataCreazione"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.add(Restrictions.between("dataCreazione", dateStart,dateEnd));
		
		criteria.createAlias("stato", "stato");
		criteria.add(Restrictions.eq("stato.codGruppoLingua", "I"));
		criteria.add(Restrictions.eq("stato.lang", "IT"));
		criteria.createAlias("rNewsletterArticolos", "rNewsletterArticolos",DetachedCriteria.INNER_JOIN);
		criteria.createAlias("rNewsletterArticolos.articolo", "articolo",DetachedCriteria.INNER_JOIN); 
		criteria.add(Restrictions.eq("articolo.categoria", categoriaMailinglist)); 

		criteria.setProjection(Projections.distinct(Projections.countDistinct("id")));
		@SuppressWarnings("unchecked")
		Long conta = (Long) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return conta;
	   
	} 
	public NewsletterCustom getNewsLetterFromCategory(long idCategory,int anno,int elementiPerPagina, int numeroPagina) throws Throwable{
		Long totaleElementi = contaNewsLetterFromCategory(idCategory, anno);
		 
		if(anno==0)
		{
			anno = Calendar.getInstance().get(Calendar.YEAR);
		}
		String dataStartString="01/01/"+anno;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date dateStart= dateFormat.parse(dataStartString);
		
		String dataEndString="31/12/"+anno;
		SimpleDateFormat dateFormatEnd = new SimpleDateFormat("dd/MM/yyyy");
		Date dateEnd= dateFormatEnd.parse(dataEndString);
		
		
		@SuppressWarnings("unchecked")
		CategoriaMailinglist categoriaMailinglist = (CategoriaMailinglist) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(DetachedCriteria.forClass( CategoriaMailinglist.class ).add( Restrictions.eq("id", idCategory) )) );
		
		DetachedCriteria criteria = DetachedCriteria.forClass(Newsletter.class)
				.addOrder(Order.desc("dataCreazione"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.add(Restrictions.between("dataCreazione", dateStart,dateEnd));
		
		criteria.createAlias("stato", "stato");
		criteria.add(Restrictions.eq("stato.codGruppoLingua", "I"));
		criteria.add(Restrictions.eq("stato.lang", "IT"));
		criteria.createAlias("rNewsletterArticolos", "rNewsletterArticolos",DetachedCriteria.INNER_JOIN);
		criteria.createAlias("rNewsletterArticolos.articolo", "articolo",DetachedCriteria.INNER_JOIN); 
		criteria.add(Restrictions.eq("articolo.categoria", categoriaMailinglist));
		//List<Newsletter> listAll = getHibernateTemplate().findByCriteria(criteria);
		int indiceStrat = elementiPerPagina * (numeroPagina - 1);
		if (totaleElementi.intValue() < indiceStrat) {
			indiceStrat = 0;
		}
		@SuppressWarnings("unchecked")
		List<Newsletter> listAll =getHibernateTemplate().findByCriteria(criteria, indiceStrat, indiceStrat+elementiPerPagina);
		return new NewsletterCustom(totaleElementi.intValue(),listAll); 
		
	}
	 
	 public List<NewsletterArticleCustom> getNewsletterArticleFormCategory(long idCategory,int anno,int elementiPerPagina, int numeroPagina) throws Throwable{
		 List<NewsletterArticleCustom> newsletterArticleCustomsReturn= new ArrayList<>();
		
		 NewsletterCustom newsletterCustom=this.getNewsLetterFromCategory(idCategory,anno, elementiPerPagina, numeroPagina);
		 
		 LinkedHashMap<String, Newsletter> singola=new LinkedHashMap<>();
		
		 for(Newsletter newsletter_: newsletterCustom.getLstNewsletter()){
			 singola.put(newsletter_.getId()+"", newsletter_);
		 }

		 
		 for(Newsletter newsletter: singola.values()){
				DetachedCriteria criteria = DetachedCriteria.forClass(RNewsletterArticolo.class)
						.addOrder(Order.desc("id")); 
				criteria.add(Restrictions.isNull("dataCancellazione"));
				criteria.add(Restrictions.eq("newsletter",newsletter));
				 
				@SuppressWarnings("unchecked")
				List<RNewsletterArticolo> rNewsletterArticolos = (List<RNewsletterArticolo>)getHibernateTemplate().findByCriteria(criteria);
				
				List<Articolo> articolos= new ArrayList<Articolo>();
				
				for(RNewsletterArticolo r:rNewsletterArticolos){
					articolos.add(r.getArticolo());
				}
				
				newsletterArticleCustomsReturn.add(new NewsletterArticleCustom(newsletter, articolos));
		 }
		 
		 new NewsletterArticleListCustom(newsletterCustom.getTotale(), newsletterArticleCustomsReturn);
		 
		 return newsletterArticleCustomsReturn;
	 }
	//MS
	 public NewsletterArticleListCustom getNewsletterArticleFormCategory_(long idCategory,int anno,int elementiPerPagina, int numeroPagina) throws Throwable{
		 List<NewsletterArticleCustom> newsletterArticleCustomsReturn= new ArrayList<>();
		
		 NewsletterCustom newsletterCustom=this.getNewsLetterFromCategory(idCategory,anno, elementiPerPagina, numeroPagina);
		 
		 LinkedHashMap<String, Newsletter> singola=new LinkedHashMap<>();
		
		 for(Newsletter newsletter_: newsletterCustom.getLstNewsletter()){
			 singola.put(newsletter_.getId()+"", newsletter_);
		 }

		 
		 for(Newsletter newsletter: singola.values()){
				DetachedCriteria criteria = DetachedCriteria.forClass(RNewsletterArticolo.class)
						.addOrder(Order.desc("id")); 
				criteria.add(Restrictions.isNull("dataCancellazione"));
				criteria.add(Restrictions.eq("newsletter",newsletter));
				 
				@SuppressWarnings("unchecked")
				List<RNewsletterArticolo> rNewsletterArticolos =(List<RNewsletterArticolo>)getHibernateTemplate().findByCriteria(criteria);
				
				List<Articolo> articolos= new ArrayList<Articolo>();
				
				for(RNewsletterArticolo r:rNewsletterArticolos){
					articolos.add(r.getArticolo());
				}
				
				newsletterArticleCustomsReturn.add(new NewsletterArticleCustom(newsletter, articolos));
		 }
		 
		return new NewsletterArticleListCustom(newsletterCustom.getTotale(), newsletterArticleCustomsReturn);
		 
		 
	 }
	 
	 public NewsletterArticleListCustom getNewsletterArticleFormCategorySottoCategory_(long idCategory,int anno,long idSottoCategory,int elementiPerPagina, int numeroPagina) throws Throwable{
			
		 if(idSottoCategory==0){
			 return this.getNewsletterArticleFormCategory_(idCategory,anno, elementiPerPagina, numeroPagina); 
		 }
		 
		 List<NewsletterArticleCustom> newsletterArticleCustomsReturn= new ArrayList<>();
		
		 NewsletterCustom newsletterCustom=this.getNewsLetterFromCategory(idCategory,anno, elementiPerPagina, numeroPagina);
		 
		 LinkedHashMap<String, Newsletter> singola=new LinkedHashMap<>();
		
		 for(Newsletter newsletter_: newsletterCustom.getLstNewsletter()){
			 singola.put(newsletter_.getId()+"", newsletter_);
		 }

		 
		 for(Newsletter newsletter: singola.values()){
				DetachedCriteria criteria = DetachedCriteria.forClass(RNewsletterArticolo.class)
						.addOrder(Order.desc("id")); 
				criteria.add(Restrictions.isNull("dataCancellazione"));
				criteria.add(Restrictions.eq("newsletter",newsletter));
				 
				@SuppressWarnings("unchecked")
				List<RNewsletterArticolo> rNewsletterArticolos =(List<RNewsletterArticolo>)getHibernateTemplate().findByCriteria(criteria);
				
				List<Articolo> articolos= new ArrayList<Articolo>();
				boolean articoloSottocategory=false;
				for(RNewsletterArticolo r:rNewsletterArticolos){
					articolos.add(r.getArticolo());//-- prendo tutti gli articoli legati alla newsletter che contiene anche un solo articolo legato alla sottocategoria come richiesto.
				if(r.getArticolo().getSottoCategoria()!=null && r.getArticolo().getSottoCategoria().getId()==idSottoCategory){
					 //articolos.add(r.getArticolo());
					  articoloSottocategory=true;
				}
				}// se ci sono articoli filtrati per sottocategoria legati alla newsletter li addiziono alla lista 
				if(articoloSottocategory)
				newsletterArticleCustomsReturn.add(new NewsletterArticleCustom(newsletter, articolos));
		 }
		 
		 
		return new NewsletterArticleListCustom(newsletterCustom.getTotale(), newsletterArticleCustomsReturn);
		 
		 
	 }
	
	 
	 
	 public List<NewsletterArticleCustom> getNewsletterArticleFormCategorySottoCategory(long idCategory,int anno,long idSottoCategory,int elementiPerPagina, int numeroPagina) throws Throwable{
		
		 if(idSottoCategory==0){
			 return this.getNewsletterArticleFormCategory(idCategory,anno, elementiPerPagina, numeroPagina); 
		 }
		 
		 List<NewsletterArticleCustom> newsletterArticleCustomsReturn= new ArrayList<>();
		
		 NewsletterCustom newsletterCustom=this.getNewsLetterFromCategory(idCategory,anno, elementiPerPagina, numeroPagina);
		 
		 LinkedHashMap<String, Newsletter> singola=new LinkedHashMap<>();
		
		 for(Newsletter newsletter_: newsletterCustom.getLstNewsletter()){
			 singola.put(newsletter_.getId()+"", newsletter_);
		 }

		 
		 for(Newsletter newsletter: singola.values()){
				DetachedCriteria criteria = DetachedCriteria.forClass(RNewsletterArticolo.class)
						.addOrder(Order.desc("id")); 
				criteria.add(Restrictions.isNull("dataCancellazione"));
				criteria.add(Restrictions.eq("newsletter",newsletter));
				 
				@SuppressWarnings("unchecked")
				List<RNewsletterArticolo> rNewsletterArticolos =(List<RNewsletterArticolo>)getHibernateTemplate().findByCriteria(criteria);
				
				List<Articolo> articolos= new ArrayList<Articolo>();
				boolean articoloSottocategory=false;
				for(RNewsletterArticolo r:rNewsletterArticolos){
				
				if(r.getArticolo().getSottoCategoria()!=null && r.getArticolo().getSottoCategoria().getId()==idSottoCategory){
					  articolos.add(r.getArticolo());
					  articoloSottocategory=true;
				}
				}// se ci sono articoli filtrati per sottocategoria legati alla newsletter li addiziono alla lista 
				if(articoloSottocategory)
				newsletterArticleCustomsReturn.add(new NewsletterArticleCustom(newsletter, articolos));
		 }
		 
		 
		 
		 return newsletterArticleCustomsReturn;
	 }
	
	@Override
	public List<NewsArticlePageHome> getNewsArticlePageHome(String lingua) throws Throwable {
		 List<CategoriaMailinglist> categoriaMailinglists=this.getCategoriaPadre(lingua);
		 List<NewsArticlePageHome> articlePageHomes = new ArrayList<>();
		 
		 
		 DetachedCriteria criteriaNewsLetter= DetachedCriteria.forClass(Newsletter.class)
		 .addOrder(Order.desc("dataCreazione")); 
		 criteriaNewsLetter.createAlias("stato", "stato");
		 criteriaNewsLetter.add(Restrictions.eq("stato.codGruppoLingua", "I"));
		 criteriaNewsLetter.add(Restrictions.eq("stato.lang", "IT"));
		 @SuppressWarnings("unchecked")
		List<Newsletter> listNewsletter = (List<Newsletter>)getHibernateTemplate().findByCriteria(criteriaNewsLetter);
	 
		 DetachedCriteria criteria = DetachedCriteria.forClass(RNewsletterArticolo.class)
					.addOrder(Order.desc("id")); 
			criteria.add(Restrictions.isNull("dataCancellazione"));
			long id =(listNewsletter!=null && listNewsletter.size()>0)?listNewsletter.get(0).getId():0;
			long idArticoloHighligths=(listNewsletter!=null && listNewsletter.size()>0 && listNewsletter.get(0).getHighLights()!=null)?listNewsletter.get(0).getHighLights().longValue():0;
			
			criteria.add(Restrictions.eq("newsletter.id",id));
		 
			@SuppressWarnings("unchecked")
			List<RNewsletterArticolo> rNewsArticolos = (List<RNewsletterArticolo>)getHibernateTemplate().findByCriteria(criteria);
			List<Articolo> articolis = new ArrayList<Articolo>();
			 
			 for(RNewsletterArticolo rw:rNewsArticolos){
				 if(rw.getArticolo()!=null)
				 articolis.add(rw.getArticolo());
			 }
			 if(articolis!=null && articolis.size()>2)
			 Collections.sort(articolis, new Comparator<Articolo>() {
				  @Override
				  public int compare(Articolo a1, Articolo a2) {
				    return a1.getDataCreazione().compareTo(a2.getDataCreazione());
				  }
				});	
			
		 
		 for(CategoriaMailinglist ctg:categoriaMailinglists){
		 
//		 List<Articolo> articolos = getHibernateTemplate().findByCriteria(
//				 DetachedCriteria.forClass(Articolo.class)
//				 .addOrder(Order.desc("dataCreazione"))
//				 .add(Restrictions.eq("categoria", ctg))
//				 .add(Restrictions.isNull("dataCancellazione")),0,2);
		 
//			   select * from newsletter s where id_stato ='7' and s.data_cancellazione is not null
//		                  order by s.data_creazione desc
//			 
			 ///////QUI
			 /*
		 			DetachedCriteria criteriaRNews = DetachedCriteria.forClass(RNewsletterArticolo.class)
							.addOrder(Order.desc("id")); 
		 			criteriaRNews.add(Restrictions.isNull("dataCancellazione"));
		 		 
		 			
		 			criteriaRNews.createAlias("newsletter", "newsletter",DetachedCriteria.INNER_JOIN);
		 			criteriaRNews.createAlias("newsletter.stato", "stato");
		 			criteriaRNews.add(Restrictions.eq("stato.codGruppoLingua", "I"));
		 			criteriaRNews.add(Restrictions.eq("stato.lang", "IT"));
		 			criteriaRNews.createAlias("articolo", "articolo",DetachedCriteria.INNER_JOIN); 
		 			criteriaRNews.add(Restrictions.eq("articolo.categoria", ctg));
		 			criteriaRNews.add(Restrictions.isNull("articolo.dataCancellazione"));

					List<RNewsletterArticolo> rNewsletterArticolos =getHibernateTemplate().findByCriteria(criteriaRNews);
					
					List<Articolo> articoli = new ArrayList<Articolo>();
					 
					 for(RNewsletterArticolo rw:rNewsletterArticolos){
						 if(rw.getArticolo()!=null)
						 articoli.add(rw.getArticolo());
					 }
					 if(articoli!=null && articoli.size()>2)
					 Collections.sort(articoli, new Comparator<Articolo>() {
						  @Override
						  public int compare(Articolo a1, Articolo a2) {
						    return a1.getDataCreazione().compareTo(a2.getDataCreazione());
						  }
						});
					 */
					 //articlePageHomes.add(new NewsArticlePageHome(ctg,articolos));
			 List<Articolo> articoli = new ArrayList<Articolo>();
			 Articolo articoloHighligths=null;
			 for(Articolo ar:articolis){
				 if(ar.getCategoria().getId()==ctg.getId()){
					 articoli.add(ar);
					 if(ar.getId()==idArticoloHighligths){
						 articoloHighligths =ar;
						 
					 }	 
				 }
				
				 
			 }
//					 List<Articolo> articoliPrimiDue = new ArrayList<Articolo>();
//					 if(articoli!=null && articoli.size()>2){
//						 articoliPrimiDue.add(articoli.get(0));
//						 articoliPrimiDue.add(articoli.get(1));
//					 }
//					 else{ 
//						 articoliPrimiDue=articoli; 
//						 }
					 articlePageHomes.add(new NewsArticlePageHome(ctg,articoli,articoloHighligths));
		 
		 }
		 
		 
		return articlePageHomes;
	}
	
	public Articolo getArticolo(long idArticolo) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Articolo.class).add(Restrictions.eq("id", idArticolo));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		Articolo articolo = (Articolo) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));

		return articolo;
	}
	
	public Newsletter getNewsletterFromArticoli(long idArticle) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(RNewsletterArticolo.class);
		criteria.createAlias("articolo", "articolo");
		criteria.add(Restrictions.eq("articolo.id", idArticle));
		
		@SuppressWarnings("unchecked")
		List<RNewsletterArticolo> lista = getHibernateTemplate().findByCriteria(criteria);
		if( lista != null && lista.size() >  0){
		return	lista.get(0).getNewsletter();
		}
		
		return null;
	}
	
	@Override
	public Collection<Integer> getAnniDisponibili() throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Articolo.class)
				.addOrder(Order.desc("dataCreazione"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		List<Articolo> articolos = (List<Articolo>)getHibernateTemplate().findByCriteria(criteria);
		LinkedHashMap<String, Integer>linkedHashMap = new LinkedHashMap<>();
		for(Articolo a:articolos){
		Calendar calendar= Calendar.getInstance();
		calendar.setTime(a.getDataCreazione());
		int anno =calendar.get(java.util.Calendar.YEAR);
		linkedHashMap.put(anno+"", new Integer(anno));
		}
		return linkedHashMap.values();	 
	}
	
	@Override
	public List<Documento> leggiAllegati(long idArticolo) throws Throwable {
	
		DetachedCriteria criteria = DetachedCriteria.forClass(DocumentoArticolo.class);
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.createAlias("articolo", "articolo");
		criteria.add(Restrictions.eq("articolo.id", idArticolo));
		
		List<Documento> allegati= new ArrayList<Documento>();
		@SuppressWarnings("unchecked")
		List<DocumentoArticolo> lsAllegati = (List<DocumentoArticolo>)getHibernateTemplate().findByCriteria(criteria);
		if( lsAllegati != null && lsAllegati.size() >  0){
		 for(DocumentoArticolo dc:lsAllegati ){
			 allegati.add(dc.getDocumento());
		 }
		}
		 
		return allegati;
	}	
	
	}

