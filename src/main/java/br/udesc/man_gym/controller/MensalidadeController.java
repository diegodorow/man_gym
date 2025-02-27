package br.udesc.man_gym.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.SessionScope;

import br.udesc.man_gym.messages.JSFMensageiro;
import br.udesc.man_gym.model.Cliente;
import br.udesc.man_gym.model.Mensalidade;
import br.udesc.man_gym.repository.MensalidadeRepository;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;

@Named
@SessionScope
public class MensalidadeController {
	private Mensalidade mensalidade = new Mensalidade();
	private Cliente cliente = new Cliente();

	private List<Mensalidade> mensalidades;
	private boolean modoEdicao = false;

	private int mes;// Quantidade de meses que o cliente contratou
	private boolean pagamento; // Situação de pagamento

	@Autowired
	private MensalidadeRepository mensalidadeDao;

	@PostConstruct
	public void init() {
		if (cliente.getMatricula() == 0) {
			setMensalidades(this.mensalidadeDao.findAll());
		}else {
		setMensalidades(this.mensalidadeDao.buscaMensalidadesPorCliente(cliente.getMatricula()));
		}
	}

	public void salvar() {
		
		try {
			if (mes == 0 || modoEdicao) {
				mensalidade.setPago(pagamento);
				mensalidadeDao.save(mensalidade);
				setModoEdicao(false);
				mensalidade = new Mensalidade();
				setMensalidades(this.mensalidadeDao.buscaMensalidadesPorCliente(cliente.getMatricula()));
			} else {
				for (int i = 1; i <= mes; i++) {
					this.mensalidade = new Mensalidade();
					this.mensalidade.mesesContratado(i, pagamento,getCliente());// Método Responsável por criar objeto(Mensalidade)
					if (mensalidade.getDataContrato() != null) {
						mensalidadeDao.save(mensalidade);
						this.mensalidades.add(mensalidade);
						System.out.println("Salvo com Sucesso");			
					} else {
						JSFMensageiro.error("ERRO!!  na hora de Criar objeto e Cadastrar");
					}

				}
				mes=0;
				
			}
		} catch (Exception ex) {
			JSFMensageiro.error("ERRO!!  na hora de Cadastrar");
			
		}

		

	}

	public void excluir(Mensalidade mensalidade) {
		try {
			mensalidadeDao.delete(mensalidade);
			mensalidades.remove(mensalidade);
			JSFMensageiro.info("Excluído com Sucesso");
		} catch (Exception ex) {
			JSFMensageiro.error("ERRo!!  na hora de Cadastrar");
		}
		this.modoEdicao = false;
	}

	public void alterarMensalidade(Mensalidade mensalidadeEscolhida) {
		this.modoEdicao = true;
		setMensalidade(mensalidadeEscolhida);
	}

	public void cancelar() {
		this.modoEdicao = false;
		this.mensalidade = new Mensalidade();
	}

	public String verContrato() {
		setMensalidades(mensalidadeDao.buscaMensalidadesPorCliente(cliente.getMatricula()));
		return "mensalidade";
	}

	public Mensalidade getMensalidade() {
		return mensalidade;
	}

	public void setMensalidade(Mensalidade mensalidade) {
		this.mensalidade = mensalidade;
	}

	public List<Mensalidade> getMensalidades() {
		return mensalidades;
	}

	public void setMensalidades(List<Mensalidade> mensalidades) {
		this.mensalidades = mensalidades;
	}

	public boolean isModoEdicao() {
		return modoEdicao;
	}

	public void setModoEdicao(boolean modoEdicao) {
		this.modoEdicao = modoEdicao;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public boolean isPagamento() {
		return pagamento;
	}

	public void setPagamento(boolean pagamento) {
		this.pagamento = pagamento;
	}

}
