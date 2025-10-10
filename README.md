# 🎵 SORISOOP


<p align="center">
  <img width="80%" src="https://github.com/user-attachments/assets/ef8fcbf2-56e4-4dbe-9370-941d82e9e91b" alt="Description">
</p>


## 🌈 프로젝트 소개

### 🔗 서비스 주소  
[**https://sorisoop.n-e.kr**](https://sorisoop.n-e.kr)

### 🎈 서비스 개요
**[AI 기반 맞춤형 동화 플랫폼]**

소리숲은 AI를 활용하여 아이에게 맞춤형 독서 경험을 제공하는 동화플랫폼입니다.
<br>
아이가 그린 그림을 바탕으로 새로운 동화를 생성하고, 부모의 목소리를 적용한 낭독 기능과 접근성을 고려한 서비스 설계를 통해 특별한 독서 경험을 즐길 수 있습니다.
<br>
또한 3D 가상 전시 기능을 통해 예술 작품을 입체적으로 감상할 수 있는 디지털 전시 공간을 제공하므로써 아이의 창의력과 표현력을 자연스럽게 확장하고, 창작의 즐거움을 경험할 수 있도록 하였습니다.

<br>

### 📆 진행 기간 
2025.8 - 2024.9 ( 6주 )

<br>
  
### 👨🏻‍💻 개발 인원

|         <img src="https://github.com/sangzun-han.png" width="150">          |   <img src="https://github.com/hyeon8571.png" width="150">   | <img src="https://github.com/aswe0409.png" width="150">   |
| :----------------------------------------------------------------------: | :---------------------------------------------------------: | :---------------------------------------------------------: |
| [한상준<br>@sangzun-han](https://github.com/sangzun-han)<br/>`팀장` | [원승현<br>@hyeon8571](https://github.com/hyeon8571)<br/>`팀원` | [정석영<br>@aswe0409](https://github.com/aswe0409)<br/>`팀원` |


<br>

### 📌 주요기능 소개

#### 💡 쇼핑몰 기본 기능
상품 조회, 장바구니 담기, 결제 등 쇼핑몰 기본 기능을 이용할 수 있습니다.

<table>
<tr>
<td width="30%" valign="top">
<div>
<p style="font-weight: bold">메인 페이지</p>
  <img src="https://github.com/user-attachments/assets/216f4113-a210-4041-baf1-953aca8cdb9f" width="220px" />
</div>
</td>
<td width="30%" valign="top">
<div>
<p style="font-weight: bold">상품 상세 조회</p>
  <img src="https://github.com/user-attachments/assets/050fa452-2080-423c-a0b9-82275b52c49a" width="215px" />
</div>
</td>
</tr>
</table>

<table>
<tr>
<td width="30%" valign="top">
<div>
<p style="font-weight: bold">친환경 상품 조회</p>
  <img src="https://github.com/user-attachments/assets/9e7d8246-725a-4491-a6b2-77dd6eeee136" width="220px" />
</div>
</td>
<td width="30%" valign="top">
<div>
<p style="font-weight: bold">장바구니 기능</p>
  <img src="https://github.com/user-attachments/assets/5a8abe7f-8ed9-46e9-b20a-9752539b86f5" width="220px" />
</div>
</td>
<td width="30%" valign="top">
<div>
<p style="font-weight: bold">상품 주문 기능</p>
  <img src="https://github.com/user-attachments/assets/c907d872-d2ce-4a00-8a7b-e5ba09bb24e2" width="210px" />
</div>
</td>
</tr>
</table>

<hr>

#### 💡 상품 추천 기능
사용자의 활동 로그(구매 이력, 검색 기록 등)를 바탕으로 상품을 추천합니다.
<br>
활동 로그가 없는 경우 나이, 성별을 바탕으로 추천하여 콜드스타트 문제를 해결했습니다.
<br>

<table>
<tr>
<td width="30%" valign="top">
<div>
<p style="font-weight: bold">산책 기능</p>
  <img src="https://github.com/user-attachments/assets/cc7bf8dc-3eab-4e52-89cc-92a4b342cc94" width="220px" />
</div>
</td>
</tr>
</table>


<hr>

#### 💡 특가 할인 기능
매일 오후 7시 ~ 8시 사이 특가 이벤트를 합니다.
<br>
트래픽이 몰리는 상황에 대비하여 리팩토링과 부하테스트를 진행했습니다.
<br>

| 담당자 | 주요 기여 | 기술 스택 | 개선 효과 |
|--------|-----------|------------|------------|
| **원승현** | 재고 선차감 처리 로직 개선 | Redis, Lua Script | <ul><li>에러율: 20% → **1%**</li><li>TPS: 75 -> **155**</li></ul> |
| **한상준** | 주문 순차 처리 시스템 설계 | Java `BlockingQueue` | <ul><li>에러율: 20% → **0%**, 커넥션 풀 고갈 현상 완화</li><li>응답 시간 증가(큐 대기 발생)</li></ul> |


<table>
<tr>
<td width="30%" valign="top">
<div>
<p style="font-weight: bold">산책 기록</p>
  <img src="https://github.com/user-attachments/assets/52313ae3-53b5-4ce4-8f3a-8bb3cdc071e7" width="220px" />
</div>
</td>
</tr>
</table>

<hr>

#### 💡 나무 키우기 기능
친환경 상품을 구매하면 얻을 수 있는 그린 포인트로 3D 나무를 키울 수 있습니다.
<br>
사용자는 이 과정을 통해 환경에 기여하고 있다는 몰입형 경험을 할 수 있습니다.
<br>
특정 레벨을 달성하면 리워드를 얻을 수 있습니다.

<table>
<tr>
<td width="30%" valign="top">
<div>
<p style="font-weight: bold">나무 키우기 기능</p>
  <img src="https://github.com/user-attachments/assets/f490faa1-477f-4fa2-8fe4-7f68aca12be2" width="220px" />
</div>
</td>
<td width="30%" valign="top">
<div>
<p style="font-weight: bold">리워드 확인</p>
  <img src="https://github.com/user-attachments/assets/ee492c93-6d16-4ea1-98c6-daa4a488b2be" width="220px" />
</div>
</td>
</tr>
</table>

<hr>

#### 💡 관리자 대시보드
서비스 운영 현황을 시각화해 제공하는 관리자 전용 대시보드입니다.
<br>
상품 판매 데이터를 기반으로 카테고리, 연령대, 성별 기준의 통계를 제공하는 분석 리포트를 제공합니다.

<table>
<tr>
<td width="100%" valign="top">
<div>
<p style="font-weight: bold">관리자 대시보드</p>
  <img src="https://github.com/user-attachments/assets/4ab0a912-18db-4d29-98e6-8f740eb71d6e" width="700px" />
</div>
</td>
</tr>
</table>
<table>
<tr>
<td width="100%" valign="top">
<div>
<p style="font-weight: bold">분석 리포트</p>
  <img src="https://github.com/user-attachments/assets/1d626536-e20e-4abd-8d27-fef25316ead1" width="700px" />
</div>
</td>
</tr>
</table>

<hr>

<br>



### ⚒️ 기술 스택

**프레임워크 및 라이브러리**
<div>
  <span><img src="https://img.shields.io/badge/React-61DAFB?style=flat-square&logo=react&logoColor=black"/></span>
  <span><img src="https://img.shields.io/badge/tailwindcss-%2338B2AC.svg?style=flat-square&logo=tailwind-css&logoColor=white"/></span>
  <span><img src="https://img.shields.io/badge/Axios-5A29E4?style=flat-square&logo=axios&logoColor=white"/></span>
  <span><img src="https://img.shields.io/badge/vite-%23646CFF.svg?style=flat-square&logo=vite&logoColor=white"</span>
</div>
<div>
  <span><img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=flat-square&logo=springboot&logoColor=white"/></span>
  <span><img src="https://img.shields.io/badge/Spring Security-6DB33F?style=flat-square&logo=springsecurity&logoColor=white"/></span>
  <span><img src="https://img.shields.io/badge/JWT-B041FF?style=flat-square&logo=jsonwebtokens&logoColor=white"/></span>
  <span><img src="https://img.shields.io/badge/MyBatis-B31B1B?style=flat-square&logo=mybatis&logoColor=white"/></span>
</div>

<br>

**데이터베이스**
<div>
  <span><img src="https://img.shields.io/badge/Oracle-F80000?style=flat-square&logo=oracle&logoColor=white"/></span>
</div>

<br>

**인프라 및 배포**
<div>
  <span><img src="https://img.shields.io/badge/Docker-2496ED?style=flat-square&logo=docker&logoColor=white"/></span>
  <span><img src="https://img.shields.io/badge/jenkins-%232C5263.svg?style=flat-square&logo=jenkins&logoColor=white"/></span>
  <span><img src="https://img.shields.io/badge/Oracle-F80000?style=flat-square&logo=oracle&logoColor=white"/></span>
  <span><img src="https://img.shields.io/badge/AWS-FF9900?style=flat-square&logo=amazonec2&logoColor=white"/></span>
  <span><img src="https://img.shields.io/badge/Nginx-009639?style=flat-square&logo=nginx&logoColor=white"/></span>
</div>

<br>

**메시지 큐**
<div>
  <span><img src="https://img.shields.io/badge/RabbitMQ-FF6600?style=flat-square&logo=rabbitmq&logoColor=white"/></span>
</div>

<br>



## <img align="center" width="50" src="https://github.com/user-attachments/assets/2e9357e9-30a3-4de4-9db6-b753a952ec55"> 시스템 아키텍처
<p align="center">
  <img width="80%" src="https://github.com/user-attachments/assets/7fee11e5-caee-4e28-95f0-3997043c1262"> 
</p>


