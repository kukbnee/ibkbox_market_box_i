package com.ibk.sb.restapi.app.common.constant;

public enum StatusCode {

    //    PROCESS_SUCCESS (200, "정보 처리가 완료 되었습니다."),
    //    ERROR_PROCESS (400, "정보 처리 중 오류가 발생하였습니다."),

    /** Business Exception Code Enum List **/

    /** 기존 MNB의 message.properties 참조 **/

    /** api error code **/
    COM0000 ("COM0000", "시스템오류가 발생하였습니다. 관리자에게 문의 바랍니다."), // MNB에서 COM0000, COM0001 중 COM0000만 사용함
    COM0001 ("COM0001", "시스템오류가 발생하였습니다. 관리자에게 문의 바랍니다."),
    COM0002 ("COM0002", "RestAPI서비스가 존재하지 않습니다. 관리자에게 문의 바랍니다."),
    COM0003 ("COM0003", "파일경로 폴더 생성 실패."),
    COM0004 ("COM0004", "temp파일 삭제 실패."),
    COM0005 ("COM0005", "잘못된 접근입니다."),
    COM0006 ("COM0006", "본인인증번호 복호화에 실패하였습니다. 시스템관리자에게 문의 하세요."),
    COM0007 ("COM0007", "본인인증번호가 없습니다."),
    COM9998 ("COM9998", "사용자 정보가 없습니다. (API호출 실패)"),
    COM9999 ("COM9999", "사용자ID가 없습니다."),

    MNB0001("MNB0001", "서버와의 통신이 원활하지 않습니다."),
    MNB0002("MNB0002", "저장에 실패했습니다."),
    MNB0003("MNB0003", "검색을 실패했습니다."),
    MNB0004("MNB0004", "데이터 수정에 실패했습니다."),
    MNB0005("MNB0005", "시스템이 불안정하여 공통코드 배포를 처리하지 못하였습니다. 다시 시도해주세요."),
    MNB0006("MNB0006", "메뉴 생성시 오류가 발생했습니다.시스템관리자에게 문의하세요."),
    MNB0007("MNB0007", "데이터 삭제에 실패했습니다."),

    /** sso message code **/
    SSO10001("SSO10001", "존재하지 않는 아이디이거나, 패스워드가 일치하지 않습니다."),
    SSO10003("SSO10003", "비밀번호가 일치하지 않습니다."),
    SSO10010("SSO10010", "비밀번호 입력 5회 이상 연속 오류 시에는 본인확인을 거친 후 비밀번호 재설정을 해야 합니다."),
    SSO10030("SSO10030", "비밀번호 변경 주기(90일)가 되었습니다. 비밀번호를 변경하시기 바랍니다."),
    SSO10040("SSO10040", "변경주기(90일)주기 7일전입니다. 비밀번호를 변경하시기바랍니다."),
    SSO10601("SSO10601", "BOX 장기 미접속 상태로 사용잠금 상태입니다. 비밀번호 재설정을 통하여 사용잠금을 해지하시기 바랍니다."),
    SSO10501("SSO10501", "시스템 장애오류(OPEN GW API)입니다. 시스템관리자에게 문의하세요."),
    SSO10511("SSO10511", "시스템 장애오류(OPEN GW API)입니다. 시스템관리자에게 문의하세요."),
    SSO10512("SSO10512", "시스템 장애오류(OPEN GW API)입니다. 시스템관리자에게 문의하세요."),

    /*로그인 연동 system message */
    LOGIN0001 ("LOGIN0001", "유요한 로그인 토큰이 존재하지 않습니다"),
    LOGIN_NOT_USER_INFO ("LOGIN0002", "사용자 정보가 존재하지 않습니다"),

    /*위시리스트 system message*/
    WISH0001 ("WISH0001", "등록된 위시리스트 입니다."),
    WISH0002 ("WISH0002", "위시리스트 불가한 상품입니다."),
    WISH0003 ("WISH0003", "삭제하려는 위시리스트 데이터가 없습니다."),
    WISH0004 ("WISH0004", "판매중지된 상품은 위시리스트에 추가 불가합니다."),

    /*에이전시 system message*/
    AGENCY0001 ("AGENCY0001", "에이전시 요청이 불가능한 상품입니다."),
    AGENCY0002 ("AGENCY0002", "에이전시 요청이 완료되었습니다."),
    AGENCY0003 ("AGENCY0003", "정회원인 경우만 에이전시 요청이 가능합니다."),
    AGENCY0004 ("AGENCY0004", "요청 혹은 승인중인 에이전시 요청이 있습니다."),
    AGENCY0005 ("AGENCY0005", "에이전시 승인 요청 되었습니다."),
    AGENCY0006 ("AGENCY0006", "에이전시 취소는 요청상태에서만 가능합니다."),
    AGENCY0007 ("AGENCY0007", "에이전시 요청 취소는 요청상태 및 보낸사람만 가능합니다."), //마이페이지 > 에이전시 요청(상품)
    AGENCY0008 ("AGENCY0008", "에이전시 요청 반려는 요청상태 및 받는사람만 가능합니다."), //마이페이지 > 에이전시 요청(상품)
    AGENCY0009 ("AGENCY0009", "승인 가능한 에이전시 요청이 없습니다."),
    AGENCY0010 ("AGENCY0010", "에이전시 승인이 완료되었습니다."),
    AGENCY0011 ("AGENCY0011", "에이전시 승인취소는 승인상태에서만 가능합니다."),
    AGENCY0012 ("AGENCY0012", "원판매자에 의해 에이전시 승인취소 되었습니다."),
    AGENCY0013 ("AGENCY0013", "승인취소상태에서만 승인취소해제가 가능합니다."),
    AGENCY0014 ("AGENCY0014", "승인취소해제가 완료되었습니다."),

    /*에이전시 user message*/
    AGENCY1000 ("AGENCY1000", "에이전시 요청 드립니다."),
    AGENCY1001 ("AGENCY1001", "에이전시 요청 취소합니다."),

    /*마이페이지 system message*/
    DELIVERY0001 ("DELIVERY0001", "수정하려는 배송정보가 올바르지 않습니다."),

    /*장바구니 system message*/
    BASKET0001("BASKET0001", "존재하지 않는 상품입니다."),
    BASKET0002("BASKET0002", "판매 중지 상품입니다."),
    BASKET0003("BASKET0002", "판매 보류 상품입니다."),

    /*주문관련 system message*/
    ORDER0001 ("ORDER0001", "구매가 가능한 회원상태가 아닙니다."),
    ORDER0002 ("ORDER0002", "주문정보가 올바르지 않습니다."),

    ORDER0003 ("ORDER0003", "상품을 확인바랍니다."),
    ORDER0004 ("ORDER0004", "주소를 확인바랍니다."),
    ORDER0005 ("ORDER0005", "배송비를 확인바랍니다."),
    ORDER0006 ("ORDER0006", "운송업체를 확인바랍니다."),
    ORDER0007 ("ORDER0007", "주문 수량을 확인바랍니다."),
    ORDER0008 ("ORDER0008", "상품명을 확인바랍니다."),
    ORDER0009 ("ORDER0009", "상품 정보 ID를 확인바랍니다."),
    ORDER0010 ("ORDER0010", "주문 유형 ID를 확인바랍니다."),
    ORDER0011 ("ORDER0011", "배송 유형 ID를 확인바랍니다."),
    ORDER0012 ("ORDER0012", "같은 배송지 사용 여부를 확인바랍니다."),
    ORDER0013 ("ORDER0013", "견적 정보 ID를 확인바랍니다."),
    ORDER0014 ("ORDER0014", "판매가격을 확인바랍니다."),
    ORDER0015 ("ORDER0015", "상품 출고지를 확인바랍니다."),
    ORDER0016 ("ORDER0016", "배송중 상태가 아닙니다."),
    ORDER0017 ("ORDER0017", "주문 정보 ID를 확인바랍니다."),
    ORDER0018 ("ORDER0018", "주문 정보 순번을 확인바랍니다."),
    ORDER0019 ("ORDER0019", "주문 상태가 아닙니다."),
    ORDER0020 ("ORDER0020", "본인 상품은 주문이 불가능합니다."),
    ORDER0021 ("ORDER0021", "상품의 배송비 유형을 확인바랍니다."),
    ORDER0022 ("ORDER0022", "택배사 유형 ID를 확인바랍니다."),
    ORDER0023 ("ORDER0023", "운송장 번호를 확인바랍니다."),
    ORDER0024 ("ORDER0024", "직접배송 또는 무료배송인 경우 가능합니다."),
    ORDER0025 ("ORDER0025", "결제 또는 취소된 견적입니다."),
    ORDER0026 ("ORDER0026", "주문 또는 배송중 상태가 아닙니다."),
    ORDER0027 ("ORDER0027", "기타 택배사명을 확인바랍니다."),
    ORDER0028 ("ORDER0028", "결제유형 ID를 확인바랍니다."),
    ORDER0029 ("ORDER0029", "결제상태 ID를 확인바랍니다."),
    ORDER0030 ("ORDER0030", "결제금액을 확인바랍니다."),
    ORDER0031 ("ORDER0031", "체결 번호 ID를 확인바랍니다."),
    ORDER0032 ("ORDER0032", "배송 업체 정보 ID를 확인바랍니다."),
    ORDER0033 ("ORDER0033", "주문취소 승인 상태가 아닙니다."),
    ORDER0034 ("ORDER0034", "주문 정보 순번 리스트를 확인바랍니다."),
    ORDER0035 ("ORDER0035", "배송요청 중 오류가 발생하였습니다."),
    ORDER0036 ("ORDER0036", "주문취소 완료 상태가 아닙니다."),
    ORDER0037 ("ORDER0037", "운송취소 중 오류가 발생하였습니다."),
    ORDER0038 ("ORDER0038", "수량에 따른 배송 업체 정보 ID를 찾을 수 없습니다."),

    /*견적 system message*/
    ESTIMATION0001 ("ESTIMATION0001", "견적요청이 가능한 회원상태가 아닙니다."),
    ESTIMATION0002 ("ESTIMATION0002", "주소를 확인바랍니다."),
    ESTIMATION0003 ("ESTIMATION0003", "배송비를 확인바랍니다."),
    ESTIMATION0004 ("ESTIMATION0004", "운송업체를 확인바랍니다."),
    ESTIMATION0005 ("ESTIMATION0005", "상품을 확인바랍니다."),
    ESTIMATION0006 ("ESTIMATION0006", "주문 수량을 확인바랍니다."),
    ESTIMATION0007 ("ESTIMATION0007", "상품명을 확인바랍니다."),
    ESTIMATION0008 ("ESTIMATION0008", "상품 정보 ID를 확인바랍니다."),
    ESTIMATION0009 ("ESTIMATION0009", "직접입력의 상품 정보 ID를 확인바랍니다."),
    ESTIMATION0010 ("ESTIMATION0010", "견적이 취소할 수 없는 상태입니다."),
    ESTIMATION0011 ("ESTIMATION0011", "연결된 문의를 찾을 수 없습니다."),
    ESTIMATION0012 ("ESTIMATION0012", "배송 유형 ID를 확인바랍니다."),
    ESTIMATION0013 ("ESTIMATION0013", "견적 정보 ID를 확인바랍니다."),
    ESTIMATION0014 ("ESTIMATION0014", "본인에게 견적발송할 수 없습니다."),
    ESTIMATION0015 ("ESTIMATION0015", "화물서비스 API 호출에 실패하였습니다."),
    ESTIMATION0016 ("ESTIMATION0016", "인감 이미지 파일 ID 를 확인바랍니다."),
    ESTIMATION0017 ("ESTIMATION0017", "타인 상품을 견적발송할 수 없습니다."),
    ESTIMATION1001 ("ESTIMATION1001", "견적이 발송되었습니다."),
    ESTIMATION1002 ("ESTIMATION1002", "견적서 전달드립니다."),
    ESTIMATION1003 ("ESTIMATION1003", "견적이 취소되었습니다."),
    ESTIMATION1004 ("ESTIMATION1004", "견적 취소상세 보기"),
    ESTIMATION1005 ("ESTIMATION1005", "견적이 결제되었습니다."),

    /*견적관련 system message*/
    ESTIM0001 ("ESTIM0001", "주문 가능한 견적서 정보가 아닙니다."),

    /*카테고리 system message*/
    CATEG0001 ("CATEG0001", "카테고리정보가 올바르지 않습니다."),
    
    /*인감정보 메세지*/
    SEAL0001 ("SEAL0001", "인감 정보가 존재하지 않습니다");
    
    private final String code;
    private final String message;

    StatusCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
