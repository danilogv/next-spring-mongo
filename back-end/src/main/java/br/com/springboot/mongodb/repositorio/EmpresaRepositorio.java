package br.com.springboot.mongodb.repositorio;

import br.com.springboot.mongodb.dominio.Empresa;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRepositorio extends MongoRepository<Empresa,String> {

    Boolean existsByCnpj(String cnpj);

}
