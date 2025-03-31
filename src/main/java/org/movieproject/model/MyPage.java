//package org.movieproject.model;
//
///*
// * 로그인한 회원의 닉네임, 비밀번호 값을 저장
// * 저장한 nickname, password를 데이터베이스에서 일치하는 값을 찾는다.
// * 예매 정보 확인 번호를 입력하면 티켓 테이블에서 회원 id와 일치하는 회원 id를 list<ticket>에 저장한다.
// * 티켓id, 예약id의 좌석id 상영정보id 출력
// */
//public class MyPage {
//    private Users userId;
//    private Tickets ticketId;
//
//    public MyPage(Users userId, Tickets ticketId) {
//        this.userId = userId;
//        this.ticketId = ticketId;
//    }
//
//    public Users getUserId() {
//        return userId;
//    }
//
//    public void setUserId(Users userId) {
//        this.userId = userId;
//    }
//
//    public Tickets getTicketId() {
//        return ticketId;
//    }
//
//    public void setTicketId(Tickets ticketId) {
//        this.ticketId = ticketId;
//    }
//
//    @Override
//    public String toString() {
//        return "MyPage{" +
//                "userId=" + userId +
//                ", ticketId=" + ticketId +
//                '}';
//    }
//}
