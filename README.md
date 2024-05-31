#### 1. Tomcat 서버를 시작할 때 웹 애플리케이션이 초기화하는 과정을 설명하라.
* 애플리케이션 초기화와 함께 `@WebListner`로 등록된 `ContextLoaderListener`가 실행된다. 
  * jwp.sql 이 실행되면서 데이터베이스를 초기화한다. 
* `DispatcherServlet`는 `loadOnStartup` 옵션으로 인해서 다른 서블릿보다 먼저 초기화된다. 
  *  inti 에서 mapping 이 초기화된다. 
    * 이때 Controller 객체가 생성된다. 
    * controller에 있는 dao 객체가 생성된다.



#### 2. Tomcat 서버를 시작한 후 http://localhost:8080으로 접근시 호출 순서 및 흐름을 설명하라.
* url pattern이 `/`으로 설정되어 있기 때문에 모든 요청을 처리한다.
* mapping에 맞는 url로 특정 서블릿으로 호출하여서 요청을 처리한다.
* 데이터베이스를 연결할 경우 `ConnectionManager` 를 통해서 연결한다.
* `JdbcTemplate` 으로 데이터를 객체로 변환한다.
* 이 객체들은 `ModelAndView`에 담긴다
* `view`에서 `ModelAndView`에 담긴 객체를 사용하여서 화면을 그린다.
  * 구체적인 화면 구성은 `jsp`에서 이루어진다.
  * request에 jsp에 필요한 객체를 담는다.

#### 7. next.web.qna package의 ShowController는 멀티 쓰레드 상황에서 문제가 발생하는 이유에 대해 설명하라.
* 
