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
* 멀티 스레드 환경에서 여러 스레드가 동시에 ShowController의 execute 메소드를 실행할 경우, 
  * `ShowController`의 인스턴스 변수인 `question, answers`를 서로 간섭할 가능성이 존재한다. 
  * 예를 들어, T1이 question과 answers를 변수를 초기화한 상태에서 T2가 question과 answers를 초기화하면, T1이 사용하는 question과 answers는 T2가 초기화한 값으로 변경된다.
  * 이로 인해서 예상하지 않은 결과값이 나올 가능성이 존재한다. 
* 이러한 문제를 해결하기 위해서 `ShowController`의 인스턴스 변수인 `question, answers`를 메소드 내부에서만 사용하도록 변경해야 한다. 
  * 이렇게 하면 각 스레드가 서로의 변수에 영향을 주지 않게 된다.
  * 스레드는 함수 단위로 동작 가능하기 때문에 지역 변수를 스레드 고유로 가질 수 있다. 
* 혹은 synchronized 키워드를 사용해서 특정 영역에 한해서 동기화 처리할 수 있다. 
* 현재 경우 synchronized 키워드를 사용할 경우 자연스럽게 처리 속도가 느려지기 때문에 지역 변수로 전환한다. 
  * 지역 변수를 전환하지 말아야 할 다른 이유가 없다. 
