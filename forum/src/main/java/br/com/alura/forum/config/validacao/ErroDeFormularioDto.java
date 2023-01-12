package br.com.alura.forum.config.validacao;

// essa classe ela q vai representar um erro de validação , 
//agora vai ser um json representado por essa classe 
public class ErroDeFormularioDto {
	private String campo;
	private String erro;
	
	public ErroDeFormularioDto(String campo, String erro) {
		super();
		this.campo = campo;
		this.erro = erro;
	}

	public String getCampo() {
		return campo;
	}

	public String getErro() {
		return erro;
	}
	
	
}
