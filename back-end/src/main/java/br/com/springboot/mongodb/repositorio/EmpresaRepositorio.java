package br.com.springboot.mongodb.repositorio;

import br.com.springboot.mongodb.dominio.Empresa;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EmpresaRepositorio extends MongoRepository<Empresa,String> {

    Boolean existsByCnpj(String cnpj);
    List<Empresa> findAllByOrderByNomeAsc();
    List<Empresa> findByNomeLikeIgnoreCase(String nome);

}
