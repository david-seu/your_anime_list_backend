package david_seu.your_anime_list_backend.service.impl;

import david_seu.your_anime_list_backend.dto.AnimeDto;
import david_seu.your_anime_list_backend.exception.ResourceNotFoundException;
import david_seu.your_anime_list_backend.mapper.AnimeMapper;
import david_seu.your_anime_list_backend.model.Anime;
import david_seu.your_anime_list_backend.model.CustomFaker;
import david_seu.your_anime_list_backend.repo.IAnimeRepo;
import david_seu.your_anime_list_backend.repo.IEpisodeRepo;
import david_seu.your_anime_list_backend.service.IAnimeService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class AnimeService implements IAnimeService {

    private IAnimeRepo animeRepo;
    private IEpisodeRepo episodeRepo;

    @Override
    public AnimeDto createAnime(AnimeDto animeDto) {
        Anime anime = AnimeMapper.mapToAnime(animeDto);
        Anime savedAnime = animeRepo.save(anime);
        return AnimeMapper.mapToAnimeDto(savedAnime, 0);
    }

    @Override
    public AnimeDto getAnimeById(Long animeId) {
        Anime anime = animeRepo.findById(animeId).
                orElseThrow(() ->
                        new ResourceNotFoundException("Anime does not exist with given id: " + animeId));
        Integer numEpisodes = episodeRepo.getEpisodesByAnime(anime).size();
        return AnimeMapper.mapToAnimeDto(anime, numEpisodes);
    }

    @Override
    public List<AnimeDto> getAllAnime(Integer page){
        Integer totalPages = animeRepo.findAll(PageRequest.of(0,10)).getTotalPages();
        if(page < 0){
            page = totalPages - page;
        }
        int pageToGet = page % totalPages;
        List<Anime> animeList = animeRepo.findAnimeByOrderByScore(PageRequest.of(pageToGet,10));
        return animeList.stream().map((anime) -> {
            Integer numEpisodes = episodeRepo.getEpisodesByAnime(anime).size();
            return AnimeMapper.mapToAnimeDto(anime, numEpisodes);
        }).collect(Collectors.toList());
    }


    @Override
    public AnimeDto updateAnime(Long animeId, AnimeDto updatedAnime) {
        Anime anime = animeRepo.findById(animeId).orElseThrow(() -> new ResourceNotFoundException("Anime does not exist with given id: " + animeId));
        C:\Users\seu21\.jdks\openjdk-21\bin\java.exe -XX:TieredStopAtLevel=1 -Dspring.output.ansi.enabled=always -Dcom.sun.management.jmxremote -Dspring.jmx.enabled=true -Dspring.liveBeansView.mbeanDomain -Dspring.application.admin.enabled=true "-Dmanagement.endpoints.jmx.exposure.include=*" "-javaagent:C:\Program Files\JetBrains\IntelliJ IDEA 2023.3.6\lib\idea_rt.jar=49172:C:\Program Files\JetBrains\IntelliJ IDEA 2023.3.6\bin" -Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8 -classpath C:\Users\seu21\Documente\GitHub\your_anime_list_backend\build\classes\java\main;C:\Users\seu21\Documente\GitHub\your_anime_list_backend\build\resources\main;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\org.projectlombok\lombok\1.18.30\f195ee86e6c896ea47a1d39defbe20eb59cd149d\lombok-1.18.30.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\org.springframework.boot\spring-boot-starter-data-jpa\3.2.4\d8c5f393d9b18d96021b3e02d80e4356c5cbe0f7\spring-boot-starter-data-jpa-3.2.4.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\org.springframework.boot\spring-boot-starter-data-rest\3.2.4\c87adf55d1a018578e210ab7f1caa3c74fc838c2\spring-boot-starter-data-rest-3.2.4.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\org.springframework.boot\spring-boot-starter-websocket\3.2.4\fd02f6a9ccbc66cf84c498d0eb4e8367a400769c\spring-boot-starter-websocket-3.2.4.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\org.springframework.boot\spring-boot-starter-web\3.2.4\a74df12b71060da7c8e87f9a8c2ef4ea43fc8017\spring-boot-starter-web-3.2.4.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\net.datafaker\datafaker\2.2.0\fcaf9a097f250cb0d7860a1cb9a70af0684f85a7\datafaker-2.2.0.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\org.springframework.boot\spring-boot-starter-actuator\3.2.4\a26cd0ab56f912d2aa44504042af06f15d3d1a80\spring-boot-starter-actuator-3.2.4.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\org.springframework.boot\spring-boot-starter-aop\3.2.4\11aedc0a23c43947608f2122eed08eabe5e2994c\spring-boot-starter-aop-3.2.4.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\org.springframework.boot\spring-boot-starter-jdbc\3.2.4\7dd399e7ba19d62cae32be6e20edac37ff8fcbc0\spring-boot-starter-jdbc-3.2.4.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\org.hibernate.orm\hibernate-core\6.4.4.Final\5c9decb3c5a70bf7801d41fc32633416c26be069\hibernate-core-6.4.4.Final.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\org.springframework.data\spring-data-jpa\3.2.4\1932f90c487999575b57ad41986de96c8ebf5843\spring-data-jpa-3.2.4.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\org.springframework\spring-aspects\6.1.5\202d9da55e24fec2eda80bbc3cd87fbefc0e1256\spring-aspects-6.1.5.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\org.springframework.data\spring-data-rest-webmvc\4.2.4\557545d45abdb3772eb6be2e04500db9de7aa3d4\spring-data-rest-webmvc-4.2.4.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\org.springframework\spring-websocket\6.1.5\1e23e4966b23e049315bbaafbc57bafca01c7c80\spring-websocket-6.1.5.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\org.springframework\spring-messaging\6.1.5\d3b0857d07b167a124100c43a41296b07f09388f\spring-messaging-6.1.5.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\org.springframework.boot\spring-boot-starter-json\3.2.4\ef3f72369ce7f6f7a7b02c0b23e60ef5bdf581b1\spring-boot-starter-json-3.2.4.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\org.springframework.boot\spring-boot-starter\3.2.4\842cf7f0ed2ecfef3011f3191fc53c59ceed752\spring-boot-starter-3.2.4.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\org.springframework.boot\spring-boot-starter-tomcat\3.2.4\ffa632eeaaf1a4e807ec4bbcc1938f7d43096472\spring-boot-starter-tomcat-3.2.4.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\org.springframework\spring-webmvc\6.1.5\92809fce136e0b662dc9325529443386ba5ec2c6\spring-webmvc-6.1.5.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\org.springframework\spring-web\6.1.5\4f4e92cc52ee33260f1ee0cdc7b7a2f22d49708c\spring-web-6.1.5.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\org.yaml\snakeyaml\2.2\3af797a25458550a16bf89acc8e4ab2b7f2bfce0\snakeyaml-2.2.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\com.github.curious-odd-man\rgxgen\2.0\43d0a91c7c90a3427dc88c42853649b3eb1207e2\rgxgen-2.0.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\org.springframework.boot\spring-boot-actuator-autoconfigure\3.2.4\fc5d94aa9b147786d60379f7365cb4bf6766cdad\spring-boot-actuator-autoconfigure-3.2.4.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\io.micrometer\micrometer-jakarta9\1.12.4\354b0798834e415a64f16125e27a1a7e5dc0aee4\micrometer-jakarta9-1.12.4.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\io.micrometer\micrometer-observation\1.12.4\492deebbd9b8ab23f588428f66578e21af266e01\micrometer-observation-1.12.4.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\org.springframework\spring-aop\6.1.5\a4f596bd3c55b6cec93f0e2e7245dd0bab8afec3\spring-aop-6.1.5.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\org.aspectj\aspectjweaver\1.9.21\beaabaea95c7f3330f415c72ee0ffe79b51d172f\aspectjweaver-1.9.21.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\com.zaxxer\HikariCP\5.0.1\a74c7f0a37046846e88d54f7cb6ea6d565c65f9c\HikariCP-5.0.1.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\org.springframework\spring-jdbc\6.1.5\e8617dcddd3377c809b3e62c325fcb923163cb20\spring-jdbc-6.1.5.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\jakarta.persistence\jakarta.persistence-api\3.1.0\66901fa1c373c6aff65c13791cc11da72060a8d6\jakarta.persistence-api-3.1.0.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\jakarta.transaction\jakarta.transaction-api\2.0.1\51a520e3fae406abb84e2e1148e6746ce3f80a1a\jakarta.transaction-api-2.0.1.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\org.springframework\spring-context\6.1.5\735d1bd7372d7c53e7b31b4a9c980ce2e0b26424\spring-context-6.1.5.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\org.springframework\spring-orm\6.1.5\d2dc2b996680fcc8ae5aea294f0ce6bda5577c7c\spring-orm-6.1.5.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\org.springframework.data\spring-data-commons\3.2.4\c934470822afb9f0751915b229d6fe28ff5e1ac2\spring-data-commons-3.2.4.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\org.springframework\spring-tx\6.1.5\90e95f4c3e30f9ecaef6ba53186ed21afebba618\spring-tx-6.1.5.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\org.springframework\spring-beans\6.1.5\9ae967f467281c9bb977585ef4d5ea7351704d60\spring-beans-6.1.5.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\org.springframework\spring-core\6.1.5\6dae1b06ffacbb9abab636be2dbc6acd3b6e5d68\spring-core-6.1.5.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\org.antlr\antlr4-runtime\4.13.0\5a02e48521624faaf5ff4d99afc88b01686af655\antlr4-runtime-4.13.0.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\jakarta.annotation\jakarta.annotation-api\2.1.1\48b9bda22b091b1f48b13af03fe36db3be6e1ae3\jakarta.annotation-api-2.1.1.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\org.slf4j\slf4j-api\2.0.12\48f109a2a6d8f446c794f3e3fa0d86df0cdfa312\slf4j-api-2.0.12.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\org.springframework.data\spring-data-rest-core\4.2.4\b6c83bc28a74131c9e5a9c942c81f9a6564fea60\spring-data-rest-core-4.2.4.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\com.fasterxml.jackson.core\jackson-annotations\2.15.4\5223ea5a9bf52cdc9c5e537a0e52f2432eaf208b\jackson-annotations-2.15.4.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\com.fasterxml.jackson.core\jackson-databind\2.15.4\560309fc381f77d4d15c4a4cdaa0db5025c4fd13\jackson-databind-2.15.4.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\com.fasterxml.jackson.datatype\jackson-datatype-jsr310\2.15.4\7de629770a4559db57128d35ccae7d2fddd35db3\jackson-datatype-jsr310-2.15.4.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\com.fasterxml.jackson.module\jackson-module-parameter-names\2.15.4\e654497a08359db2521b69b5f710e00836915d8c\jackson-module-parameter-names-2.15.4.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\com.fasterxml.jackson.datatype\jackson-datatype-jdk8\2.15.4\694777f182334a21bf1aeab1b04cc4398c801f3f\jackson-datatype-jdk8-2.15.4.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\org.springframework.boot\spring-boot-autoconfigure\3.2.4\b3f481aff8f0775f44d78399c804a8c52d75b971\spring-boot-autoconfigure-3.2.4.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\org.springframework.boot\spring-boot\3.2.4\ccb7cbb30dcf1d91dbbf20a3219a457eead46601\spring-boot-3.2.4.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\org.springframework.boot\spring-boot-starter-logging\3.2.4\32616f4a33ec0fda0c54aaa67ab10dc78df3fd78\spring-boot-starter-logging-3.2.4.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\org.apache.tomcat.embed\tomcat-embed-websocket\10.1.19\adf4710fac2471236f8a466ca678cdf7e6a8257c\tomcat-embed-websocket-10.1.19.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\org.apache.tomcat.embed\tomcat-embed-core\10.1.19\3dbbca8acbd4dd6a137c3d6f934a2931512b42ce\tomcat-embed-core-10.1.19.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\org.apache.tomcat.embed\tomcat-embed-el\10.1.19\c61a582c391aca130884a5421deedfe1a96c7415\tomcat-embed-el-10.1.19.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\org.springframework\spring-expression\6.1.5\7e21cb1c6bbef1509e12d485b75ffc61278d9fa7\spring-expression-6.1.5.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\org.springframework.boot\spring-boot-actuator\3.2.4\9921454129e42a4c6806240885ed763eb874cd24\spring-boot-actuator-3.2.4.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\io.micrometer\micrometer-core\1.12.4\d5b91e2847de7c1213889c532789743eb2f96337\micrometer-core-1.12.4.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\io.micrometer\micrometer-commons\1.12.4\a57f10c78956b38087f97beae66cf14cb8b08d34\micrometer-commons-1.12.4.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\org.springframework\spring-jcl\6.1.5\896ae3519327731589c6e77521656b50ae32d5b3\spring-jcl-6.1.5.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\org.springframework.hateoas\spring-hateoas\2.2.1\4622d8f5d92fa8072266bfe1d2cc02f9963a18e\spring-hateoas-2.2.1.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\org.springframework.plugin\spring-plugin-core\3.0.0\d56aa02dd7272dca30aa598dc8b72e823227046a\spring-plugin-core-3.0.0.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\org.atteo\evo-inflector\1.3\4cf8b5f363c60e63f8b7688ac053590460f2768e\evo-inflector-1.3.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\com.fasterxml.jackson.core\jackson-core\2.15.4\aebe84b45360debad94f692a4074c6aceb535fa0\jackson-core-2.15.4.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\ch.qos.logback\logback-classic\1.4.14\d98bc162275134cdf1518774da4a2a17ef6fb94d\logback-classic-1.4.14.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\org.apache.logging.log4j\log4j-to-slf4j\2.21.1\d77b2ba81711ed596cd797cc2b5b5bd7409d841c\log4j-to-slf4j-2.21.1.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\org.slf4j\jul-to-slf4j\2.0.12\eb5f48f782b41cc881b0bf1fb4d88ae2ff6d5b93\jul-to-slf4j-2.0.12.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\com.jayway.jsonpath\json-path\2.9.0\37fe2217f577b0b68b18e62c4d17a8858ecf9b69\json-path-2.9.0.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\ch.qos.logback\logback-core\1.4.14\4d3c2248219ac0effeb380ed4c5280a80bf395e8\logback-core-1.4.14.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\org.apache.logging.log4j\log4j-api\2.21.1\74c65e87b9ce1694a01524e192d7be989ba70486\log4j-api-2.21.1.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\org.springframework.boot\spring-boot-devtools\3.2.4\ccd261700a4ff8e8f629a4d267f0b4f53ca17897\spring-boot-devtools-3.2.4.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\org.postgresql\postgresql\42.6.2\18c33c28326b2f81480833291b81f5eca080ab3c\postgresql-42.6.2.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\org.checkerframework\checker-qual\3.31.0\eeefd4af42e2f4221d145c1791582f91868f99ab\checker-qual-3.31.0.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\org.jboss.logging\jboss-logging\3.5.3.Final\c88fc1d8a96d4c3491f55d4317458ccad53ca663\jboss-logging-3.5.3.Final.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\org.hibernate.common\hibernate-commons-annotations\6.0.6.Final\77a5f94b56d49508e0ee334751db5b78e5ccd50c\hibernate-commons-annotations-6.0.6.Final.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\io.smallrye\jandex\3.1.2\a6c1c89925c7df06242b03dddb353116ceb9584c\jandex-3.1.2.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\com.fasterxml\classmate\1.6.0\91affab6f84a2182fce5dd72a8d01bc14346dddd\classmate-1.6.0.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\net.bytebuddy\byte-buddy\1.14.12\6e37f743dc15a8d7a4feb3eb0025cbc612d5b9e1\byte-buddy-1.14.12.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\org.glassfish.jaxb\jaxb-runtime\4.0.5\ca84c2a7169b5293e232b9d00d1e4e36d4c3914a\jaxb-runtime-4.0.5.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\jakarta.xml.bind\jakarta.xml.bind-api\4.0.2\6cd5a999b834b63238005b7144136379dc36cad2\jakarta.xml.bind-api-4.0.2.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\jakarta.inject\jakarta.inject-api\2.0.1\4c28afe1991a941d7702fe1362c365f0a8641d1e\jakarta.inject-api-2.0.1.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\org.glassfish.jaxb\jaxb-core\4.0.5\7b4b11ea5542eea4ad55e1080b23be436795b3\jaxb-core-4.0.5.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\jakarta.activation\jakarta.activation-api\2.1.3\fa165bd70cda600368eee31555222776a46b881f\jakarta.activation-api-2.1.3.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\org.hdrhistogram\HdrHistogram\2.1.12\6eb7552156e0d517ae80cc2247be1427c8d90452\HdrHistogram-2.1.12.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\org.latencyutils\LatencyUtils\2.0.3\769c0b82cb2421c8256300e907298a9410a2a3d3\LatencyUtils-2.0.3.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\org.eclipse.angus\angus-activation\2.0.2\41f1e0ddd157c856926ed149ab837d110955a9fc\angus-activation-2.0.2.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\org.glassfish.jaxb\txw2\4.0.5\f36a4ef12120a9bb06d766d6a0e54b144fd7ed98\txw2-4.0.5.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\com.sun.istack\istack-commons-runtime\4.1.2\18ec117c85f3ba0ac65409136afa8e42bc74e739\istack-commons-runtime-4.1.2.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\net.minidev\json-smart\2.5.0\57a64f421b472849c40e77d2e7cce3a141b41e99\json-smart-2.5.0.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\net.minidev\accessors-smart\2.5.0\aca011492dfe9c26f4e0659028a4fe0970829dd8\accessors-smart-2.5.0.jar;C:\Users\seu21\.gradle\caches\modules-2\files-2.1\org.ow2.asm\asm\9.3\8e6300ef51c1d801a7ed62d07cd221aca3a90640\asm-9.3.jar david_seu.your_anime_list_backend.YourAnimeListBackendApplication

                .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
        '  |____| .__|_| |_|_| |_\__, | / / / /
                =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.4)

        2024-04-24T17:40:59.842+03:00  INFO 22452 --- [your_anime_list_backend] [  restartedMain] d.y.YourAnimeListBackendApplication      : Starting YourAnimeListBackendApplication using Java 21 with PID 22452 (C:\Users\seu21\Documente\GitHub\your_anime_list_backend\build\classes\java\main started by seu21 in C:\Users\seu21\Documente\GitHub\your_anime_list_backend)
        2024-04-24T17:40:59.850+03:00  INFO 22452 --- [your_anime_list_backend] [  restartedMain] d.y.YourAnimeListBackendApplication      : No active profile set, falling back to 1 default profile: "default"
        2024-04-24T17:40:59.974+03:00  INFO 22452 --- [your_anime_list_backend] [  restartedMain] .e.DevToolsPropertyDefaultsPostProcessor : Devtools property defaults active! Set 'spring.devtools.add-properties' to 'false' to disable
        2024-04-24T17:40:59.975+03:00  INFO 22452 --- [your_anime_list_backend] [  restartedMain] .e.DevToolsPropertyDefaultsPostProcessor : For additional web related logging consider setting the 'logging.level.web' property to 'DEBUG'
        2024-04-24T17:41:02.509+03:00  INFO 22452 --- [your_anime_list_backend] [  restartedMain] .s.d.r.c.RepositoryConfigurationDelegate : Bootstrapping Spring Data JPA repositories in DEFAULT mode.
        2024-04-24T17:41:02.634+03:00  INFO 22452 --- [your_anime_list_backend] [  restartedMain] .s.d.r.c.RepositoryConfigurationDelegate : Finished Spring Data repository scanning in 109 ms. Found 2 JPA repository interfaces.
        2024-04-24T17:41:04.552+03:00  INFO 22452 --- [your_anime_list_backend] [  restartedMain] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port 8081 (http)
                2024-04-24T17:41:04.577+03:00  INFO 22452 --- [your_anime_list_backend] [  restartedMain] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
        2024-04-24T17:41:04.578+03:00  INFO 22452 --- [your_anime_list_backend] [  restartedMain] o.apache.catalina.core.StandardEngine    : Starting Servlet engine: [Apache Tomcat/10.1.19]
        2024-04-24T17:41:04.675+03:00  INFO 22452 --- [your_anime_list_backend] [  restartedMain] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
        2024-04-24T17:41:04.675+03:00  INF
        anime.setTitle(updatedAnime.getTitle());
        anime.setScore(updatedAnime.getScore());
        anime.setWatched(updatedAnime.getWatched());

        Anime updatedAnimeObj = animeRepo.save(anime);

        Integer numEpisodes = episodeRepo.getEpisodesByAnime(anime).size();

        return AnimeMapper.mapToAnimeDto(updatedAnimeObj, numEpisodes);
    }

    @Override
    public void deleteAnime(Long animeId) {
        animeRepo.findById(animeId).orElseThrow(() -> new ResourceNotFoundException("Anime does not exist with given id: " + animeId));

        animeRepo.deleteById(animeId);
    }

    @Override
    public AnimeDto createAnime() {
        Anime anime = new Anime(null,
                "Naruto",
                8,
                true
        );

        Anime savedAnime = animeRepo.save(anime);
        return AnimeMapper.mapToAnimeDto(savedAnime, 0);
    }

    @Override
    public AnimeDto getAnimeByTitle(String title) {
        List<Anime> animeList = animeRepo.findAll();
        for (Anime anime : animeList) {
            if (anime.getTitle().toLowerCase().strip().equals(title.toLowerCase().strip())) {
                return AnimeMapper.mapToAnimeDto(anime, 0);
            }
        }
        throw new ResourceNotFoundException("Anime does not exist with given title: " + title);
    }
    
//    @Override
//    public AnimeDto getRandomAnime() {
//        List<Anime> animeList = animeRepo.findAll();
//        if (animeList.isEmpty()) {
//            throw new ResourceNotFoundException("No animes found");
//        }
//        Anime randomAnime = animeList.get(new Random().nextInt(animeList.size()));
//        return AnimeMapper.mapToAnimeDto(randomAnime);
//    }


}
