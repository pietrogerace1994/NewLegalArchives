package eng.la.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="COLLEGIO_ARBITRALE")
public class CollegioArbitrale implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="COLLEGIO_ARBITRALE_ID_GENERATOR", sequenceName="COLLEGIO_ARBITRALE_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="COLLEGIO_ARBITRALE_ID_GENERATOR")
	private long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_AUTORIZZAZIONE")
	private Date dataAutorizzazione;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CREAZIONE")
	private Date dataCreazione;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_MODIFICA")
	private Date dataModifica;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_RICHIESTA_AUTOR_INCARICO")
	private Date dataRichiestaAutorIncarico;

	@Column(name="DENOM_STUDIO_ARBITRO_PRESIDEN")
	private String denomStudioArbitroPresiden;

	@Column(name="DENOMIN_STUDIO_ARBITRO_SEGRET")
	private String denominStudioArbitroSegret;

	@Column(name="DENOMINAZ_STUDIO_CONTROPARTE")
	private String denominazStudioControparte;

	@Column(name="INDIRIZZO_ARBITRO_CONTROPARTE")
	private String indirizzoArbitroControparte;

	@Column(name="INDIRIZZO_ARBITRO_PRESIDENTE")
	private String indirizzoArbitroPresidente;

	@Column(name="INDIRIZZO_ARBITRO_SEGRETARIO")
	private String indirizzoArbitroSegretario;
  
	@Column(name="NOME_COLLEGIO_ARBITRALE")
	private String nomeCollegioArbitrale;
 
	@Column(name="NOMINATIVO_ARBITRO_CONTROPARTE")
	private String nominativoArbitroControparte;

	@Column(name="NOMINATIVO_PRESIDENTE")
	private String nominativoPresidente;

	@Column(name="NOMINATIVO_SEGRETARIO")
	private String nominativoSegretario; 
	
	//bi-directional many-to-one association to Incarico
	@ManyToOne()
	@JoinColumn(name="ID_INCARICO")
	private Incarico incarico;
	 
	//bi-directional many-to-one association to ProfessionistaEsterno
	@ManyToOne()
	@JoinColumn(name="ID_PROFESSIONISTA_ESTERNO")
	private ProfessionistaEsterno professionistaEsterno;

}
