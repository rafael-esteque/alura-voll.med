package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.endereco.Endereco;
import med.voll.api.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("medicos")
//uma única URL /medicos serve simultaneamente para GET e POST, o programa sabe do que se trata a partir do verbo HTTP da requisição
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @Transactional
    //aqui estou dizendo:"Spring, se chegar uma requisição do tipo POST para a url /medicos, chame o método cadastrar() da classe MedicoController
    public void cadastrar(@RequestBody @Valid DadosCadastroMedico dados) {
        repository.save(new Medico(dados));
    }

    @GetMapping
    //Devemos colocar a notação para dizer qual o verbo do rpotocolo HTTP que em que esse método será chamado
    //Aqui não precisa da notação @Transactional pois é um método de apenas leitura, estou apenas carregando registro do banco de dados;
    // não estou salvando, atualizando, nem excluindo informações do banco.
    public Page<DadosListagemMedico> listar(Pageable paginacao) {
        //estamos usando esse DTO DadosListagemMedico (e não simplesmente a classe Medico inteira) pois não queremos que esse método liste
        // o telefone e o endereço do médico, apenas as outras infos.

        //o spring também tem essas ferramentas para que não precisemos fazer a paginação manualmente;

        return repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
        //lembrando: a nossa classe MedicoRepository não tem declarado um método findAll, mas o repository herda do JpaRepository, e lá
        // nessa interface existe esse método findAll. Esses método do CRUD (para salvar, listar, atualizar, excluir) já existem declarados
        // nessa interface JpaRepository, da qual herdamos.

        //sobre o novo nome do método, alterado para findAllBtAtivoTrue: existe um padrão de nomenclatura no Spring Data - se criarmos um
        // método seguindo esse padrão, o Spring consegue montar a consulta (query) e gerar o comando sql da maneira que queremos
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados) {
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);

    }

    @DeleteMapping("/{id}")
    @Transactional
    public void excluir(@PathVariable Long id) {
        //repository.deleteById(id); Essa linha serviria para uma exclusão física, que não é o que queremos
        var medico = repository.getReferenceById(id);
        medico.excluir();
    }
}
