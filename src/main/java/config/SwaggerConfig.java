package config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @Author : Licf
 * @ Описание: класс конфигурации Swagger2
 * При интеграции с Spring boot поместите его в каталог того же уровня, что и Application.java.
 * Через аннотацию @Configuration позвольте Spring загрузить этот тип конфигурации.
 * Затем используйте аннотацию @ EnableSwagger2, чтобы включить Swagger2.
 * @Date : Created in 17:02 2018/4/12
 * @Modified By : Licf
 */

/**
 @Api: украсить весь класс и описать роль контроллера
 @ApiOperation: описать метод класса или интерфейс
 @ApiParam: описание одного параметра
 @ApiModel: использовать объекты для получения параметров
 @ApiProperty: при получении параметров с объектом, опишите поле объекта
 @ApiResponse: 1 описание ответа HTTP
 @ApiResponses: общее описание ответа HTTP.
 @ApiIgnore: используйте эту аннотацию, чтобы игнорировать этот API
 @ApiError: информация, возвращаемая при возникновении ошибки
 @ApiImplicitParam: параметр запроса
 @ApiImplicitParams: несколько параметров запроса
 */
@Configuration
public class SwaggerConfig {
    /**
     * Создать приложение API
     * apiInfo () добавляет информацию, связанную с API
     * Верните экземпляр ApiSelectorBuilder с помощью функции select (), чтобы контролировать, какие интерфейсы отображаются в Swagger для отображения,
     * В этом примере для определения каталога, в котором будет создан API, используется указанный путь к отсканированному пакету.
     *
     * @return
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis (RequestHandlerSelectors.basePackage ("com/omgdendi/blps/controller/rest")) // Пакет сканирования Swagger
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * Создайте основную информацию API (основная информация будет отображаться на странице документа)
     * Адрес для посещения: http: // фактический адрес проекта / swagger-ui.html
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //Заголовок страницы
                .title ("Банк рефератов, дипломы, курсовые работы")
                // основатель
                .contact(new Contact("Licf", "http://www.loveyoursmile.top", "canfengli@126.com"))
                //Описание
                .description ("Лабораторная работа №1 по \"Бизнес-логике программных систем\": http://www.bestreferat.ru")
                //номер версии
                .version("1.0")
                .build();
    }
}