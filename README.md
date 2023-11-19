<details>
<summary> Question  </summary>
<div markdown="2">
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