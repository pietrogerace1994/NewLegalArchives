package eng.la.persistence;

import java.util.ArrayList;
import java.util.Date;
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

import eng.la.model.Articolo;
import eng.la.model.CategoriaMailinglist;
import eng.la.model.CategoriaMailinglistStyle;
import eng.la.model.Newsletter;
import eng.la.model.NewsletterEmail;
import eng.la.model.RNewsletterArticolo;
import eng.la.model.StatoNewsletter;
import eng.la.util.DateUtil;

@Component("newsletterDAO")
public class NewsletterDAOImpl extends HibernateDaoSupport implements NewsletterDAO, CostantiDAO {

	
	@Autowired
	public NewsletterDAOImpl(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	@Override
	public Newsletter leggiNewsletter(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Newsletter.class).add(Restrictions.eq("id", id));
		//criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		Newsletter newsletter = (Newsletter) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));

		return newsletter;
	}

	@Override
	public List<Newsletter> leggiNewsletter() throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Newsletter.class).addOrder(Order.asc("oggetto"));
		//criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		List<Newsletter> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Override
	public Newsletter salvaNewsletter(Newsletter vo) throws Throwable {
		getHibernateTemplate().save(vo);
		return vo;
	}

	@Override
	public void cancellaNewsletter(long id, String lingua) throws Throwable {
		Newsletter vo = leggiNewsletter(id);
		vo.setDataCancellazione(new Date());
		
		StatoNewsletter stato  = getStatoCancellata(lingua);
		
		vo.setStato(stato);
		getHibernateTemplate().update(vo);
	}

	private StatoNewsletter getStatoCancellata(String lingua) {
		DetachedCriteria criteria = DetachedCriteria.forClass( StatoNewsletter.class ).addOrder(Order.asc("id"));
		criteria.add(Restrictions.eq("lang", lingua));
		criteria.add(Restrictions.ilike("codGruppoLingua", CostantiDAO.NEWSLETTER_STATO_CANCELLATA, MatchMode.ANYWHERE));
		@SuppressWarnings("unchecked")
		StatoNewsletter lista = (StatoNewsletter) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return lista;
	}

	@Override
	public void modificaNewsletter(Newsletter vo) throws Throwable {
		getHibernateTemplate().update(vo);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Newsletter> cerca(String dal, String al, String titolo, String stato, int elementiPerPagina, int numeroPagina,
			String ordinamento, String tipoOrdinamento) throws Throwable {
		
		Long numeroTotaleElementi = conta(titolo);
		elementiPerPagina = (int) (elementiPerPagina > numeroTotaleElementi ? numeroTotaleElementi : elementiPerPagina);
  		DetachedCriteria criteria = DetachedCriteria.forClass(Newsletter.class); 
  		criteria.createAlias("stato", "stato", DetachedCriteria.INNER_JOIN);
		criteria.add(Restrictions.isNull("dataCancellazione"));
		if (ordinamento == null) {
			criteria.addOrder(Order.asc("numero"));
		} else {
			if (tipoOrdinamento == null || tipoOrdinamento.equalsIgnoreCase("ASC")) {
				criteria.addOrder(Order.asc(ordinamento));
			} else {
				criteria.addOrder(Order.desc(ordinamento));
			}
		}
		
		if( dal != null && DateUtil.isData(dal) ){
			criteria.add(Restrictions.ge("dataCreazione", DateUtil.toDate(dal)));
		}
		
		if( al != null && DateUtil.isData(al) ){
			criteria.add(Restrictions.lt("dataCreazione", DateUtil.getDataOra(al+" - 23:59:59")));
		}
		
		if( titolo != null && titolo.trim().length() > 0 ){
			criteria.add( Restrictions.ilike("titolo", titolo, MatchMode.ANYWHERE) ) ;
		}
		
		if (stato != null && stato.trim().length() > 0) {
			criteria.add(Restrictions.eq("stato.codGruppoLingua", stato));
		}

		int indicePrimoElemento = elementiPerPagina * (numeroPagina - 1);
		if (numeroTotaleElementi < indicePrimoElemento) {
			indicePrimoElemento = 0;
		}

		List<Newsletter> listaRitorno = new ArrayList<Newsletter>();
		 
		listaRitorno = (List<Newsletter>)getHibernateTemplate().findByCriteria(criteria, indicePrimoElemento, indicePrimoElemento+elementiPerPagina );
		 
		return listaRitorno; 
	}
	
	@Override
	public Long conta(String titolo) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Newsletter.class);
		criteria.add(Restrictions.isNull("dataCancellazione"));
		
		
		if( titolo != null && titolo.trim().length() > 0 ){
			criteria.add( Restrictions.ilike("titolo", titolo, MatchMode.ANYWHERE) ) ;
		} 

		criteria.setProjection(Projections.distinct(Projections.countDistinct("id")));
		@SuppressWarnings("unchecked")
		Long conta = (Long) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return conta;
	}

	@Override
	public void cancellaDocumento(long documentoId) throws Throwable {
		
	}

	@Override
	public RNewsletterArticolo aggiungiArticolo(RNewsletterArticolo rnewsArt) throws Throwable {
		getHibernateTemplate().save(rnewsArt);
		return rnewsArt;
		
	}

	@Override
	public List<RNewsletterArticolo> leggiArticoliDocumento(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(RNewsletterArticolo.class);
		criteria.createAlias("newsletter", "newsletter", DetachedCriteria.INNER_JOIN);
		criteria.add(Restrictions.eq("newsletter.id", id));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		List<RNewsletterArticolo> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}
	
	@Override
	public List<Articolo> leggiArticoliDocumento() throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(RNewsletterArticolo.class);
		criteria.setProjection(Projections.distinct(Projections.property("articolo")));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		List<Articolo> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}
	
	@Override
	public List<StatoNewsletter> listaStatoNewsletter(String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( StatoNewsletter.class ).addOrder(Order.asc("id"));
		criteria.add(Restrictions.eq("lang", lingua));
		@SuppressWarnings("unchecked")
		List<StatoNewsletter> lista = getHibernateTemplate().findByCriteria(criteria);		
		return lista; 
		 
	}
	
	@Override
	public StatoNewsletter getStatoAttivo(String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( StatoNewsletter.class ).addOrder(Order.asc("id"));
		criteria.add(Restrictions.eq("lang", lingua));
		criteria.add(Restrictions.ilike("codGruppoLingua", CostantiDAO.NEWSLETTER_STATO_ATTIVA, MatchMode.ANYWHERE));
		@SuppressWarnings("unchecked")
		StatoNewsletter lista = (StatoNewsletter) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));		
		return lista; 
		 
	}

	@Override
	public void attivaNewsletter(Newsletter newsletter) throws Throwable {
		getHibernateTemplate().update(newsletter);
	}

	@Override
	public Integer getNewNumber() throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( Newsletter.class );
		criteria.addOrder(Order.asc("id"));
		criteria.setProjection(Projections.max("numero"));
			@SuppressWarnings("unchecked")
			Integer max = (Integer) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return max;
		
	}

	@Override
	public StatoNewsletter getStatoInviata(String upperCase) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( StatoNewsletter.class ).addOrder(Order.asc("id"));
		criteria.add(Restrictions.eq("lang", upperCase));
		criteria.add(Restrictions.ilike("codGruppoLingua", CostantiDAO.NEWSLETTER_STATO_INVIATA, MatchMode.ANYWHERE));
		@SuppressWarnings("unchecked")
		StatoNewsletter lista = (StatoNewsletter) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));		
		return lista; 
	}

	@Override
	public void inviaNewsletter(Newsletter news) throws Throwable {
		getHibernateTemplate().update(news);
	}

	@Override
	public List<CategoriaMailinglist> cercaCategorie(int numElementiPerPagina, int numeroPagina, String ordinamento)
			throws Throwable {
		Long numeroTotaleElementi = contaCategorie();
		numElementiPerPagina = (int) (numElementiPerPagina > numeroTotaleElementi ? numeroTotaleElementi : numElementiPerPagina);
  		DetachedCriteria criteria = DetachedCriteria.forClass(CategoriaMailinglist.class);
  		criteria.add(Restrictions.isNull("dataCancellazione"));
		
		
		criteria.addOrder(Order.asc("ord"));
		


		int indicePrimoElemento = numElementiPerPagina * (numeroPagina - 1);
		if (numeroTotaleElementi < indicePrimoElemento) {
			indicePrimoElemento = 0;
		}

		  
		@SuppressWarnings("unchecked")
		List<CategoriaMailinglist> lista = getHibernateTemplate().findByCriteria(criteria, indicePrimoElemento, indicePrimoElemento+numElementiPerPagina );
		
		List<CategoriaMailinglist> listaRitorno  = null;
		
		if( lista != null ){
			listaRitorno  = new ArrayList<CategoriaMailinglist>();
			int index = 0;
			for( CategoriaMailinglist p : lista ){
				if( index < numElementiPerPagina){	
					listaRitorno.add( leggiCategoria( p.getId() ));					
				}
			    index++;
			}
		}
		
		return listaRitorno;  	 
	}

	@Override
	public Long contaCategorie() throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(CategoriaMailinglist.class);
		criteria.add(Restrictions.isNull("dataCancellazione"));
		

		criteria.setProjection(Projections.distinct(Projections.countDistinct("id")));
		@SuppressWarnings("unchecked")
		Long conta = (Long) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return conta;
	}
	
	@Override
	public CategoriaMailinglist leggiCategoria(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(CategoriaMailinglist.class)
				.add(Restrictions.eq("id", id));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		CategoriaMailinglist categoriaMailinglist = (CategoriaMailinglist) DataAccessUtils
				.uniqueResult(getHibernateTemplate().findByCriteria(criteria));

		return categoriaMailinglist;
	}

	@Override
	public CategoriaMailinglist salvaCategoria(CategoriaMailinglist vo) throws Throwable {
		getHibernateTemplate().save(vo);
		return vo;
	}

	@Override
	public void modificaCategoria(CategoriaMailinglist vo) throws Throwable {
		getHibernateTemplate().update(vo);
		
	}

	@Override
	public void cancellaCategoria(long id) throws Throwable {
		CategoriaMailinglist vo = leggiCategoria(id);
		vo.setDataCancellazione(new Date());
		getHibernateTemplate().update(vo);
		
	}

	@Override
	public Long findColor(String colore) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(CategoriaMailinglistStyle.class)
				.add(Restrictions.eq("colore", colore));
		@SuppressWarnings("unchecked")
		CategoriaMailinglistStyle categoriaMailinglist = (CategoriaMailinglistStyle) DataAccessUtils
				.uniqueResult(getHibernateTemplate().findByCriteria(criteria));

		return categoriaMailinglist.getId();
	}

	@Override
	public NewsletterEmail aggiungiEmail(NewsletterEmail nemail) throws Throwable {
		getHibernateTemplate().save(nemail);
		return nemail;
		
	}

	@Override
	public List<NewsletterEmail> leggiEmails(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(NewsletterEmail.class);
		criteria.createAlias("newsletter", "newsletter", DetachedCriteria.INNER_JOIN);
		criteria.add(Restrictions.eq("newsletter.id", id));
		@SuppressWarnings("unchecked")
		List<NewsletterEmail> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Override
	public void cancellaFigliCategoria(long id) throws Throwable {
		List<CategoriaMailinglist> figlie = leggiCategorieFiglie(id, false);
		
		for(CategoriaMailinglist figlia:figlie){
			figlia.setDataCancellazione(new Date());
			getHibernateTemplate().update(figlia);
		}
	} 
	
	public List<CategoriaMailinglist> leggiCategorieFiglie(long idPadre, boolean tutte) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(CategoriaMailinglist.class)
				.addOrder(Order.asc("nomeCategoria"));
		if(!tutte){
			criteria.add(Restrictions.isNull("dataCancellazione"));
		}
		criteria.add(Restrictions.eq("categoriaPadre.id", idPadre));
		@SuppressWarnings("unchecked")
		List<CategoriaMailinglist> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Override
	public Long contaFigliCategoria(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(CategoriaMailinglist.class);
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.add(Restrictions.eq("categoriaPadre.id", id));
		
		criteria.setProjection(Projections.distinct(Projections.countDistinct("id")));
		@SuppressWarnings("unchecked")
		Long conta = (Long) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return conta;
	}

	@Override
	public void rimuoviArticolo(RNewsletterArticolo articolo) throws Throwable {
		articolo.setDataCancellazione(new Date());
		getHibernateTemplate().update(articolo);
	}
	
//	@Override
//	public List<DocumentoNewsletter> leggiNewsletterDocbyId(long id) throws Throwable {
//		DetachedCriteria criteria = DetachedCriteria.forClass( DocumentoNewsletter.class );
//		criteria.add(Restrictions.isNull("dataCancellazione"));
//		criteria.createAlias("articolo", "articolo");
//		criteria.add( Restrictions.eq("articolo.id", id));
//		@SuppressWarnings("unchecked")
//		List<DocumentoNewsletter> lista = getHibernateTemplate().findByCriteria(criteria);
//		return lista; 
//	}
//	
//	@Override
//	public void cancellaDocumento(long documentoId) throws Throwable {
//		DetachedCriteria criteria = DetachedCriteria.forClass(DocumentoNewsletter.class);
//		criteria.createAlias("documento", "documento");		
//		criteria.add(Restrictions.eq("documento.id", documentoId));
//		DocumentoNewsletter vo = (DocumentoNewsletter) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );
//		vo.setDataCancellazione(new Date());
//		getHibernateTemplate().update(vo);
//	}
}
