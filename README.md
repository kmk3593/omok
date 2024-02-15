# 프로젝트 : Omok

- **개요** : 다른 사람과 오목게임을 하며 결과를 공유하고, 채팅으로 소통도 가능한 웹사이트

---

## 참여자

- **김민균**, **황우빈**

---

## 개발 기간

- 2023.12.26 ~ 2024.02.01

---

## 개발 기록

- https://docs.google.com/spreadsheets/d/1rhRL6poqGYN1prdUAXa7R7bxcEM9RLzlgoDNFomsLtc/edit#gid=0

---

## 사용 기술

- **SpringBoot** (**ver** : 3.2.1)
- **Spring-Security**
- **WebSocket**
- **H2 DataBase**
- **Lombok**
- **Thymeleaf**
- **JQuery**
    - Ajax
- **GitHub** (https://github.com/kmk3593/omok/tree/master)

---

## DataBase : H2

- **ERD** :
    
    ![Untitled](https://github.com/kmk3593/omok/assets/81403633/90604b42-5be8-49c1-b963-2badb7814830)
    
- **DB 정보:**

    ![Untitled](https://github.com/kmk3593/omok/assets/81403633/8dabb3d8-a929-4018-831d-3d2d004f560a)

    ![Untitled](https://github.com/kmk3593/omok/assets/81403633/67a3c916-4e92-4e20-8852-349bfcf7ed2e)

- **ORM** (Hibernate**) :**
    
    ![Untitled](https://github.com/kmk3593/omok/assets/81403633/26de4461-7f83-4387-8a5a-2770c3bab93f)

    
- **SQL Mapper** (MyBatis) :
    
    ![Untitled](https://github.com/kmk3593/omok/assets/81403633/526eff22-ddb4-40d2-ad45-e66e83e5c5c9)

    

---

## 패키지 구조

    ![Untitled](https://github.com/kmk3593/omok/assets/81403633/9fabcb52-11ae-4e75-a0e0-18a42714a682)

---

## 주요 기능

### 1. WebSocket 관련

- **채팅**
    - **onmessage**, **send** 등의 웹소켓 내부 함수 사용하여 구현
    - 채팅 전송 과정에서 채팅 로그를 DB 에 저장

    ![Untitled](https://github.com/kmk3593/omok/assets/81403633/aba723d3-c03c-48f4-8f9f-abf356efd9ad)

- **오목**
    - **handleTextMessage** 함수를 오버라이딩하여 구현
    - 방마다 각기 다른 **UUID** 코드를 기반으로 데이터를 뿌림
        - **UUID** : 일련의 숫자와 문자로 구성된 128비트 값
    
    ![Untitled](https://github.com/kmk3593/omok/assets/81403633/c34ccc55-b030-4dab-b8b9-7e1ff16ad2bd)
    
    ![Untitled](https://github.com/kmk3593/omok/assets/81403633/5a742f98-14a3-4b29-97e8-efa33beab1da)
    

### 2. Spring Security 관련

- **로그인**
    - **loadUserByUsername** 함수를 **오버라이딩**하여 로그인 기능을 구현
    
    ![Untitled](https://github.com/kmk3593/omok/assets/81403633/52f0a547-1d43-4d89-9ebd-a7f1208b1e38)
    
    ![Untitled](https://github.com/kmk3593/omok/assets/81403633/f7793950-1817-402f-87cb-354587b462e8)
    

### 3. 정규표현식 관련

- **회원 가입**
    - **정규표현식**을 사용하여 데이터를 구분
    
    ![Untitled](https://github.com/kmk3593/omok/assets/81403633/42bedaef-d1aa-4f25-847a-902d69f56813)
    
    ![Untitled](https://github.com/kmk3593/omok/assets/81403633/7bd1441d-51ec-4ac8-ae57-5fac01fac1cd)
    
    ![Untitled](https://github.com/kmk3593/omok/assets/81403633/cae1988f-6e75-4846-9152-bdc45ad07e43)
    
- **채팅**
    - 공백으로 시작하는 문자열 구분

    ![Untitled](https://github.com/kmk3593/omok/assets/81403633/4ad4974a-3d1b-4d4f-b537-161347d900c1)

---

### 프로젝트를 마치며…

1. ERD 구조대로 테이블을 만들려고 했었으나 **@Entity** 어노테이션을 이용하여 자동 생성을 하다보니 실제 컬럼명은 더 길어지게 되었음
- 다음번엔 **@Column** 어노테이션의 **name** 속성을 이용하여 컬럼명을 명시해줄것을 고려중
2. security의 권한별로 접근을 허가하는 기능들을 아예 배제했는데, 다음번엔 시도해볼 의향이 있음
3. model 이외에도 session 으로 값을 전달해보려 시도했으나 생각 이상으로 시간이 들었기에 보류

