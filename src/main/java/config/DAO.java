package config;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import repository.PessoaRepository;
import repository.PremioRepository;

public class DAO {

    private static final AnnotationConfigApplicationContext ctx
            = new AnnotationConfigApplicationContext(DBConfig.class);
    public static PessoaRepository pessoaRepository = ctx.getBean(PessoaRepository.class);
    public static PremioRepository premioRepository = ctx.getBean(PremioRepository.class);

}
