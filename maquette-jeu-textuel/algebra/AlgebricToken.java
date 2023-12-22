package algebra;

public class AlgebricToken {

	private AlgebricTypeDeToken algebricTypeDeToken;
	private String valeur;

	public AlgebricToken(AlgebricTypeDeToken algebricTypeDeToken, String value) {
		this.algebricTypeDeToken = algebricTypeDeToken;
		this.valeur=value;
	}

	public AlgebricToken(AlgebricTypeDeToken algebricTypeDeToken) {
		this.algebricTypeDeToken = algebricTypeDeToken;
	}

	public AlgebricTypeDeToken getTypeDeToken() {
		return algebricTypeDeToken;
	}

	public String getValeur() {
		return valeur;
	}

	public String toString() {
		String res = algebricTypeDeToken.toString();
		if (valeur != null) res = res + "(" + valeur + ")";
		return res;
	}

}
