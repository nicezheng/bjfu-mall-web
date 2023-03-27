package bjfu.BJFU.mall;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@MapperScan("bjfu.BJFU.mall.dao")
@SpringBootApplication
public class BjfuMallApplication {
    public static void main(String[] args) {
        SpringApplication.run(BjfuMallApplication.class, args);
    }
}
