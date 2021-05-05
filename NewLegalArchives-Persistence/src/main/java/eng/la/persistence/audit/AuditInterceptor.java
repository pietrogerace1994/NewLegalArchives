package eng.la.persistence.audit;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.sql.SQLException;
import java.util.Date;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import eng.la.model.AbstractEntity;
import eng.la.model.Atto;
import eng.la.model.Fascicolo;
import eng.la.model.Incarico;
import eng.la.model.Proforma;
import eng.la.model.RIncaricoProformaSocieta;
import eng.la.model.Utente;
import eng.la.model.audit.AuditedAttribute;
import eng.la.model.audit.AuditedObjectName;
import eng.la.persistence.AttoDAO;
import eng.la.persistence.FascicoloDAO;
import eng.la.persistence.IncaricoDAO;
import eng.la.persistence.ProformaDAO;
import eng.la.util.CurrentSessionUtil;
import eng.la.util.DateUtil;
import eng.la.util.SpringUtil;
import eng.la.util.costants.Costanti;

public class AuditInterceptor extends EmptyInterceptor {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(AuditInterceptor.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
		auditOperation(entity);
		return super.onSave(entity, id, state, propertyNames, types);
	}

	@Override
	public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState,
			String[] propertyNames, Type[] types) {
		auditOperation(entity);
		return super.onFlushDirty(entity, id, currentState, previousState, propertyNames, types);
	}

	private void auditOperation(Object entity) {
		try {
			if (entity.getClass().getSuperclass() != null
					&& entity.getClass().getSuperclass().equals(AbstractEntity.class)) {
				AbstractEntity abstractEntity = (AbstractEntity) entity;
				String dataOraOperazione = abstractEntity.getOperationTimestamp() != null
						? DateUtil.getDatayyyymmddHHmmssdupunti(abstractEntity.getOperationTimestamp().getTime())
						: DateUtil.getDatayyyymmddHHmmssdupunti(new Date().getTime());
						
				String nomeServer = null;
				try{
					nomeServer = InetAddress.getLocalHost().getHostName();
					//if( nomeServer.indexOf(".") != -1 )
						//nomeServer = nomeServer.substring(0, nomeServer.indexOf("."));
				}catch (Exception e) {
					e.printStackTrace();
				}
				
				if( nomeServer == null ){
					nomeServer = System.getProperty("server.name");
				}
				String instanceDbName = System.getProperty("instance.db.name");
				String nomeApp = Costanti.NOME_APPLICAZIONE;
				String tipoAzione = abstractEntity.getOperation();
				String nomeOggetto = getNomeOggetto(entity);
				CurrentSessionUtil sessionUtil = (CurrentSessionUtil) SpringUtil.getBean("currentSessionUtil");
				String userId = sessionUtil.getUserId();
				String opzionale = "";
				StringBuffer valorePrimaMod = new StringBuffer("");
				StringBuffer valoreDopoMod = new StringBuffer("");
				String note = "";
				if (abstractEntity.getOperation() != null) {
					 
					if( entity instanceof Incarico ){
						opzionale = ((Incarico) entity).getCollegioArbitrale().equals("T") ? "Collegio arbitrale":"Incarico";
						opzionale = opzionale.toUpperCase();
					}else{
						opzionale = entity.getClass().getName().substring(entity.getClass().getName().lastIndexOf(".")+1, entity.getClass().getName().length()).toUpperCase();	
					}
					
					if (abstractEntity.getOperation().equals(AbstractEntity.UPDATE_OPERATION)) {
						note = "UPDATE DEL RECORD";
						if (abstractEntity instanceof Fascicolo) {
							Fascicolo vo = (Fascicolo) abstractEntity;
							FascicoloDAO dao = (FascicoloDAO) SpringUtil.getBean("fascicoloDAO");
							Fascicolo voOld = dao.leggiSenzaHibernate(vo.getId());
							
							nomeOggetto = instanceDbName +"\\"+nomeOggetto;
							verificaModificheApportate(vo, voOld, valorePrimaMod, valoreDopoMod);
						} else if (abstractEntity instanceof Incarico) {
							Incarico vo = (Incarico) abstractEntity;
							IncaricoDAO dao = (IncaricoDAO) SpringUtil.getBean("incaricoDAO");
							Incarico voOld = dao.leggiSenzaHibernate(vo.getId());
							nomeOggetto += "\\Fascicolo di riferimento: " + vo.getFascicolo().getNome();
							nomeOggetto = instanceDbName +"\\"+nomeOggetto;
							verificaModificheApportate(vo, voOld, valorePrimaMod, valoreDopoMod);

						} else if (abstractEntity instanceof Proforma) {
							Proforma vo = (Proforma) abstractEntity;
							ProformaDAO dao = (ProformaDAO) SpringUtil.getBean("proformaDAO");
							Proforma voOld = dao.leggiSenzaHibernate(vo.getId());
							IncaricoDAO incaricoDAO = (IncaricoDAO) SpringUtil.getBean("incaricoDAO");
							RIncaricoProformaSocieta incaricoProformaSocieta = vo.getRIncaricoProformaSocietas().iterator().next();
							Incarico incarico = incaricoDAO.leggiSenzaHibernate(incaricoProformaSocieta.getIncarico().getId());
							FascicoloDAO fascdao = (FascicoloDAO) SpringUtil.getBean("fascicoloDAO");
							Fascicolo fascicolo = fascdao.leggiSenzaHibernate(incarico.getFascicolo().getId());
							nomeOggetto += "\\Fascicolo di riferimento: " + fascicolo.getNome();
							nomeOggetto = instanceDbName +"\\"+nomeOggetto;
							verificaModificheApportate(vo, voOld, valorePrimaMod, valoreDopoMod);

						} else if (abstractEntity instanceof Atto) {
							Atto vo = (Atto) abstractEntity;
							AttoDAO dao = (AttoDAO) SpringUtil.getBean("attoDAO");
							Atto voOld = dao.leggiSenzaHibernate(vo.getId());
							if(vo.getFascicolo()!=null)
								nomeOggetto += "\\Fascicolo di riferimento: " + vo.getFascicolo().getNome();

							nomeOggetto = instanceDbName +"\\"+nomeOggetto;
							verificaModificheApportate(vo, voOld, valorePrimaMod, valoreDopoMod);

						}
					} else if (abstractEntity.getOperation().equals(AbstractEntity.INSERT_OPERATION)) {
						note = "INSERIMENTO NUOVO RECORD";
						if (abstractEntity instanceof Incarico) {
							Incarico vo = (Incarico) abstractEntity; 
							nomeOggetto += "\\Fascicolo di riferimento: " + vo.getFascicolo().getNome(); 

							nomeOggetto = instanceDbName +"\\"+nomeOggetto;
						} else if (abstractEntity instanceof Proforma) {
							Proforma vo = (Proforma) abstractEntity; 
							//IncaricoDAO incaricoDAO = (IncaricoDAO) SpringUtil.getBean("incaricoDAO");
							RIncaricoProformaSocieta incaricoProformaSocieta = vo.getRIncaricoProformaSocietas().iterator().next();
							Incarico incarico = incaricoProformaSocieta.getIncarico();
							nomeOggetto += "\\Fascicolo di riferimento: " + incarico.getFascicolo().getNome();

							nomeOggetto = instanceDbName +"\\"+nomeOggetto;
						} else if (abstractEntity instanceof Atto) {
							Atto vo = (Atto) abstractEntity;
							
							if(vo.getFascicolo()!=null)
								nomeOggetto += "\\Fascicolo di riferimento: " + vo.getFascicolo().getNome();

							nomeOggetto = instanceDbName +"\\"+nomeOggetto;
						}
					} else if (abstractEntity.getOperation().equals(AbstractEntity.DELETE_OPERATION)) {
						note = "CANCELLAZIONE LOGICA DEL RECORD";
						if (abstractEntity instanceof Incarico) {
							Incarico vo = (Incarico) abstractEntity; 
							nomeOggetto += "\\Fascicolo di riferimento: " + vo.getFascicolo().getNome(); 

							nomeOggetto = instanceDbName +"\\"+nomeOggetto;
						} else if (abstractEntity instanceof Proforma) {
							Proforma vo = (Proforma) abstractEntity; 
							IncaricoDAO incaricoDAO = (IncaricoDAO) SpringUtil.getBean("incaricoDAO");
							RIncaricoProformaSocieta incaricoProformaSocieta = vo.getRIncaricoProformaSocietas().iterator().next();
							Incarico incarico = incaricoDAO.leggi(incaricoProformaSocieta.getIncarico().getId());
							nomeOggetto += "\\Fascicolo di riferimento: " + incarico.getFascicolo().getNome();

							nomeOggetto = instanceDbName +"\\"+nomeOggetto;
						} else if (abstractEntity instanceof Atto) {
							Atto vo = (Atto) abstractEntity;
							if(vo.getFascicolo()!=null)
								nomeOggetto += "\\Fascicolo di riferimento: " + vo.getFascicolo().getNome();

							nomeOggetto = instanceDbName +"\\"+nomeOggetto;
						}
					} else if (abstractEntity.getOperation().equals(AbstractEntity.READ_OPERATION)) {
						note = "VISUALIZZAZIONE DETTAGLIO OGGETTO";
						if (abstractEntity instanceof Incarico) {
							Incarico vo = (Incarico) abstractEntity; 
							nomeOggetto += "\\Fascicolo di riferimento: " + vo.getFascicolo().getNome(); 

							nomeOggetto = instanceDbName +"\\"+nomeOggetto;
						} else if (abstractEntity instanceof Proforma) {
							Proforma vo = (Proforma) abstractEntity; 
							IncaricoDAO incaricoDAO = (IncaricoDAO) SpringUtil.getBean("incaricoDAO");
							RIncaricoProformaSocieta incaricoProformaSocieta = vo.getRIncaricoProformaSocietas().iterator().next();
							Incarico incarico = incaricoDAO.leggi(incaricoProformaSocieta.getIncarico().getId());
							nomeOggetto += "\\Fascicolo di riferimento: " + incarico.getFascicolo().getNome();

							nomeOggetto = instanceDbName +"\\"+nomeOggetto;
						} else if (abstractEntity instanceof Atto) {
							Atto vo = (Atto) abstractEntity;
							if(vo.getFascicolo()!=null)
								nomeOggetto += "\\Fascicolo di riferimento: " + vo.getFascicolo().getNome();

							nomeOggetto = instanceDbName +"\\"+nomeOggetto;
						}else{

							nomeOggetto = instanceDbName +"\\"+nomeOggetto;
						}
					} else if (abstractEntity.getOperation().equals(AbstractEntity.LOGIN_OPERATION)) {
						nomeOggetto = sessionUtil.getClientIp();
						note = "AUTENTICAZIONE EFFETTUATA";
					} else if (abstractEntity.getOperation().equals(AbstractEntity.LOGOUT_OPERATION)) {
						nomeOggetto = sessionUtil.getClientIp();
						note = "DISCONNESSIONE EFFETTUATA";
					}

					
					String separatore = "|";
					StringBuffer log = new StringBuffer();
					log.append(dataOraOperazione).append(separatore);
					log.append(nomeServer).append(separatore);
					log.append(nomeApp).append(separatore);
					log.append(tipoAzione).append(separatore);
					log.append(nomeOggetto).append(separatore);
					log.append(userId).append(separatore);
					log.append(opzionale).append(separatore);
					log.append(valorePrimaMod).append(separatore);
					log.append(valoreDopoMod).append(separatore);
					log.append(note);

					logger.info(log.toString());
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	private void verificaModificheApportate(AbstractEntity voNew, AbstractEntity voOld, StringBuffer valorePrimaMod,
			StringBuffer valoreDopoMod) {
		for (Field field : voNew.getClass().getDeclaredFields()) {

			// if method is annotated with @AuditedObjectName
			if (field.isAnnotationPresent(AuditedAttribute.class)) {
				AuditedAttribute annotation = field.getAnnotation(AuditedAttribute.class);
				Class attributeClassType = annotation.classType();
				Object oldValue = "";
				Object newValue = "";
				try {
					String attributeName = field.getName();
					String getMethodName = (field.getType().equals(Boolean.class) ? "is" : "get")
							+ StringUtils.capitalize(attributeName);
					Method getMethod = voNew.getClass().getMethod(getMethodName, null);
					oldValue = getMethod.invoke(voOld, null);
					newValue = getMethod.invoke(voNew, null);
					if( newValue instanceof Date ){
						newValue = DateUtil.formattaDataOra( ((Date) newValue ).getTime() );
						oldValue = oldValue != null ? DateUtil.formattaDataOra( ((Date) oldValue ).getTime() ): null;
					} 
					if (attributeClassType.isPrimitive() || attributeClassType.equals(String.class)
							|| attributeClassType.equals(BigDecimal.class) || attributeClassType.equals(Long.class)
							|| attributeClassType.equals(Date.class) || attributeClassType.equals(Integer.class)
							|| attributeClassType.equals(Boolean.class)) {
						if ((oldValue != null && newValue != null && !oldValue.equals(newValue))
								|| (oldValue == null && newValue != null)) {
							valorePrimaMod.append(attributeName + ": " + oldValue + "; ");
							valoreDopoMod.append(attributeName + ": " + newValue + "; ");
						}
					} else {

						String oldNome = getNomeOggetto(oldValue);
						String newNome = getNomeOggetto(newValue);
						if (oldNome != null && newNome != null && !oldNome.equals(newNome)|| 
								(oldNome == null && newNome != null)) {
							valorePrimaMod.append(attributeName + ": " + oldNome + "; ");
							valoreDopoMod.append(attributeName + ": " + newNome + "; ");
						}

					}
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private String getNomeOggetto(Object entity) {
		for (Method method : entity.getClass().getDeclaredMethods()) {

			// if method is annotated with @AuditedObjectName
			if (method.isAnnotationPresent(AuditedObjectName.class)) {
				String objectName;
				try {
					objectName = (String) method.invoke(entity, null);
					
					String name = "";
					if( entity instanceof Incarico ){
						name = ((Incarico) entity).getCollegioArbitrale().equals("T") ? "Collegio arbitrale":"Incarico";
						name = name.toUpperCase();
						name += "\\";
					}else if(  entity instanceof Fascicolo ||  entity instanceof Atto ||  entity instanceof Proforma ){
						 name = entity.getClass().getName().substring(entity.getClass().getName().lastIndexOf(".")+1, entity.getClass().getName().length()).toUpperCase();
						 name += "\\"; 
					}  
					objectName =  name + objectName ;
					return objectName;
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public void auditLoginLogout(Utente vo) {
		auditOperation(vo);
	}

	public void auditRead(AbstractEntity vo) {
		vo.setOperation(AbstractEntity.READ_OPERATION);
		vo.setOperationTimestamp(new Date());
		auditOperation(vo);
	}

}
