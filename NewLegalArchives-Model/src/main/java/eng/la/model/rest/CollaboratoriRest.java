package eng.la.model.rest;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CollaboratoriRest{
	private List<CollaboratoreRest> collaboratore;

	public List<CollaboratoreRest> getCollaboratore() {
		return collaboratore;
	}

	public void setCollaboratore(List<CollaboratoreRest> collaboratore) {
		this.collaboratore = collaboratore;
	}

	
}
	
