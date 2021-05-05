package eng.la.model.constants;
public class PartiCorrelateModel {
	public enum PartiCorrelateConstants {
		
		ATTIVA("attiva"),
        NON_ATTIVA("non attiva"),
        INDICE_SIMILITUDINE(89),
        RICERCA_ESITO_POSITIVO("positivo"),
        RICERCA_ESITO_NEGATIVO("negativo");

        private String nameStr;
        private int nameInt;

        PartiCorrelateConstants(String name) {
            this.nameStr = name;
        }

        PartiCorrelateConstants(int name) {
            this.nameInt = name;
        }

        public String getStringValue() {
            return nameStr;
        }

        public int getIntValue() {
            return nameInt;
        }

    }
}