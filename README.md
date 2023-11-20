<details>
<summary> Question  </summary>
<div markdown="1">
Question 클래스에 @Entity적용하여 JPA가 엔티티로 인식을 한다.
엔티티의 속성 고유번호-Id, 제목-subject, 내용-content, 작성일시-createDate 

* '@Id' Id속성을 기본 키로 설정하여 중복 값을 저장할 수 없게 만든다.
* '@GeneratedValue' 데이터를 저장할 때 해당 속성에 값을 따로 설정하지 않아도 1씩 증가하여 저장된다.
* '@Column' 칼럼의 세부 설정을 위해 사용.
    * 'length'는 칼럼의 길이를 설정
    * 'columnDefinition = "TEXT"'는 글자 수를 제한 할 수 없는 경우에 사용된다.


> ### *테이블의 칼럼명
위의 Question 엔티티에서 작성일시에 해당하는 createDate 속성의 실제 테이블의 컬럼명은 create_date가 된다.
즉 createDate처럼 대소문자 형태의 카멜케이스(Camel Case) 이름은 create_date 처럼 모두 소문자로 변경되고
언더바(_)로 단어가 구분되어 실제 테이블 컬럼명이 된다.

> ### *엔티티와 Setter
일반적으로 엔티티에는 세터 메소드를 구현하지 않는 방법을 권한다. 이는 엔티티는 데이터 베이스와 바로 연결되어 있어 데이터를 유연하게 변경할 수 있는 Setter메소드를 사용하는 것은 안전하지 않다고 생각을 한다.

엔티티를 생성할 경우 롬복의 @Builder를 통한 빌드팬턴을 사용하고, 데이터를 변경해야 할 경우에는 그에 해당하는 메소드를 언티티에 추가하여 데이터를 변경하면 된다.

다만, 복잡도와 원활한 학습을 위해서 @Setter를 사용한다.

</div>
</details>

<hr>

<details>
<summary> Answer  </summary>
<div markdown="2">
답변 엔티티 속성에서 마지막 question은 질문 엔티티를 참조하기 위해 추가.
( 특정 객체를 얻을려면 'answer.getQuestion().getSubject()'처럼 접근이 가능하다. )
하지만 속성만 추가하면 안되고 질문 엔티티와 연결된 속성이라고 표시를 하야 한다.

* '@ManyToOne'은 N:1 관계라고 할 수 있다. 해당 어노테이션을 설정하면 Answer엔티티의 question속성과 Question 엔티티가 서로 연결된다.
    * @ManyToOne은 부모 자식 관계를 갖는 구조에서 사용한다. 여기서 부모는 Question, 자식은 Answer라고 할 수 있다.

* '@OneToMany'는 @ManyToOne의 반대의 개념이다. 하나의 질문에 답변은 하나 이상이 가능하기 때문에 Question 엔티티에 답변 속성은 List로 구성 
</div>
</details>

<hr>

<details>
<summary> JPARepository  </summary>
<div markdown="3">

엔티티만으로는 데이터베이스에 데이터를 저장하거나 조회 할 수 없다.
데이터 처리를 위해서는 실제 데이터 베이스 연동하는 JPA 리포지터리가 필요하다.
>리포지터리란?  
리포지터리는 엔티티에 의해 생성된 데이터베이스 테이블에 접근하는 메서드들(예: findAll, save 등)을 사용하기 위한 인터페이스이다. 데이터 처리를 위해서는 테이블에 어떤 값을 넣거나 값을 조회하는 등의 CRUD(Create, Read, Update, Delete)가 필요하다. 이 때 이러한 CRUD를 어떻게 처리할지 정의하는 계층이 바로 리포지터리이다.

QuestionRepository는 JpaRepository 인터페이스를 상속했다.  
JpaRepository를 상속할 때는 제네릭스 타입으로 <Question, Integer> 처럼 리포지터리의 대상이 되는 엔티티의 타입(Question)과 해당 엔티티의 Primary Key 의 속성 타입(Integer)을 지정해야 한다.    
이것은 JpaRepository를 생성하기 위한 규칙이다.

<details>
<summary> TestCode  </summary>
<div markdown="1">

* '@Autowired' 객체를 주입하기 위해 사용하는 스프링의 어노테이션이다. 객체 주입 방법에는 @Autowired외 Setter 또는 생성자를 이용하는 방법이 있다.   @Autowired방식 보다는 생성자를 통한 객체 주입방식을 권장한다.   하지만 테스트 코드의 경우 생성자를 통한 객체의 주입 방식이 불가능하므로 테스트 코드 작성시 @Autowired방식을 사용

H2기반의 데이터 베이스는 파일 기반의 데이터 베이스이기 때문에 로컬서버에서 이미 구동중이면 오류가 발생된다. 

* .findAll:  데이터 조회 시 사용하는 메소드
* .findById : Id값으로 데이터를 조회한다. 해당 메소드의 리턴타입은 Optional이다. 
* findBySubject : Question레포지터리는 findBySubject와 같은 메소드를 기본적으로 제공하지 않는다. 그래서 레포지터리에 인터페이스를 변경해야 한다.
  * JpaRepository를 상속한 QuestionRepository객체를 생성될 때 스피링이 자동으로 QuestionRepository 객체를 생성한다. 이 때 프록시 패턴이 사용된다고 한다. 리포지터리 객체의 메서드가 실행될때 JPA가 해당 메서드명을 분석하여 쿼리를 만들고 실행한다.
* findBySubjectAndContent : 두개의 속성을 And 기준으로 조회할 때 레포지터리에 메소드를 선언한다.
  * > And - findBySubjectAndContent(String subject, String content)      
     Or - findBySubjectOrContent(String subject, String content)   
     Between - findByCreateDateBetween(LocalDateTime fromDate, LocalDateTime toDate)  등 등..
* findBySubjectLike : Like검색을 위해서 메소드의 입력 문자열로 " ~ %"와 같이 작성을 한다.
  *  ~ % -> " ~ "으로 시작하는 문자열
  *  % ~ -> " ~ "으로 끝나는 문자열
  *  % ~ % -> " ~ "를 내포하고 있는 문자열
* dataModify : 해당 데이터를 optional의 isPresent메소드를 사용하여 값이 있으면 세터를 이용하여 값을 수정한다.
* dataDelete modify : 코드와 비슷하다. 마지막에 세터 대신 delete를 한다

<hr>

* creatAnswerData : 답변 데이터를 생성하기위해 질문 데이터가 필요하므로 질문 데이터를 갖고온 다음 Answer 엔티티의 question 속성에 갖고온 질문 데이터를 대입하여 답변 데이터를 생서
* selectAnswer : id값이 1인 answer 데이터를 조회 및 question id가 2인지 확인
* answer엔티티의 질문 속성을 이용하면 답변과 연결된 질문을 조회할 수 있다. 반대로 질문에서 답변을 조회할 수 있다.
해당 테스트 코드를 그냥 실행하면 question리포지터리가 findById를 호출하여 객체를 조회하면 DB와의 세션이 끊어지기 때문에 문제가 생긴다. 해당 문제를 해결하기 위해서 @Transactional 어노테이션을 사용하여 DB세션을 유지 시킨다.
</div>
</details>

</div>
</details>

<hr>

<details>
<summary> Template  </summary>
<div markdown="4">

`@RequiredArgsConstructor`은 final이 붙은 속성을 포함하는 생성자를 자동으로 생성하는 역활을 한다. 
롬복의 @Getter,Setter처럼 자동으로 메소드를 생성하는 것과 마찬가지다.

> #### 스프링의 의존성 주입 방식   
> * `@Auot-wired` 속성 - 속성에 @Auot-wired어노테이션을 적용하여 객체를 주입
> * 생성자 - 생성자를 작성하여 객체를 주입하는 방식
> * Setter - Setter 메소드를 작성하여 객체를 주입하는 방식
>    * 메소드에 @Auot-wired 사용하여 객체를 주입

* 타임리프의 속성
  * 분기문 속성
    * ``` th:if="${question != null}" ```
    * question객체가 null이 아닌 경우 해당 엘리먼트가 표시
  * 반복문 속성
    * ``` th:each="question : ${questionList}" ```
    * 해당 코드는 for-each문과 유사하다.
    * ```th:each="question, loop : ${questionList}"```
    * 위 `loop`를 사용하여 속성을 사용할 수 있다.
  
  | loop.  | 기능                      | loop.    | 기능                                   |  
  |--------|-------------------------|----------|--------------------------------------|  
  | .index | 반복 순서, 0부터 1씩 증가        | .count   | 반복순서, 1부터 1씩 증가                      |
  | .size  | 반복 객체의 요소 갯수            | .first   | 루프의 첫번째 순서인 경우 -> true               |
  | .last  | 루프의 마지막 순서인 경우 -> true  | .odd     | 루프의 홀수번째 순서인 경우 -> true              |
  | .even  | 루프의 짝수번째 순서인 경우 -> true | .current | 현재 대입된 객체 <br/>ex) 위 경우 question과 동일 |
</div>
</details>