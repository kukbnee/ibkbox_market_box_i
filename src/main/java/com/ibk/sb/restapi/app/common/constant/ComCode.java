package com.ibk.sb.restapi.app.common.constant;

import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public enum ComCode {

    /** 공통코드 Enum **/

    // 국내외 구분 공통코드
    REGION_DOMESTIC("TMP_0001", "국내", "REGION"),
    REGION_ABROAD("TMP_0002", "해외", "REGION"),

    /*배너 유형*/
    BANNER_MAIN("BNS00001", "메인배너", "BANNER"),
    BANNER_SUB("BNS00002", "서브배너", "BANNER"),
    BANNER_PRODUCT("BNS00003", "상품배너", "BANNER"),
    BANNER_EVENT("BNS00004", "이벤트배너", "BANNER"),

    /*판매자스토어 이미지유형*/
//    SELLER_BACKGROUND("SRS01001", "배경이미지", "SELLER"),
//    SELLER_BANNER("SRS01002", "배너이미지", "SELLER"),

    /*상품 파일유형*/
    GDS05001("GDS05001", "상품이미지", "PRODUCTFILE"),
    GDS05002("GDS05002", "상품영상", "PRODUCTFILE"),
    GDS05003("GDS05003", "상품 천일화물 배송 제품이미지", "PRODUCTFILE"),

    /*관리자문의 유형*/
    AIS00001("AIS00001", "배송", "ADMINQNATYPE"),
    AIS00002("AIS00002", "구매", "ADMINQNATYPE"),
    AIS00003("AIS00003", "판매", "ADMINQNATYPE"),
    AIS00004("AIS00004", "에이전시", "ADMINQNATYPE"),
    AIS00005("AIS00005", "기타", "ADMINQNATYPE"),
    AIS00006("AIS00006", "이벤트", "ADMINQNATYPE"),

    /*관리자문의 파일유형*/
    AIS02001("AIS02001", "문의", "ADMINQNAFILE"),
    AIS02002("AIS02002", "답변", "ADMINQNAFILE"),

    /*관리자문의 처리유형*/
    AIS01001("AIS01001", "답변대기", "ADMINQNASTATUS"),
    AIS01002("AIS01002", "답변완료", "ADMINQNASTATUS"),

    /*이벤트 진행유형*/
    ETS00001("ETS00001", "진행중", "EVENTSTATUS"),
    ETS00002("ETS00002", "준비중", "EVENTSTATUS"),
    ETS00003("ETS00003", "마감", "EVENTSTATUS"),

    /*이벤트 상품 처리유형*/
    ETS01001("ETS01001", "접수", "EVENTPRODUCTSTATUS"),
    ETS01002("ETS01002", "선정", "EVENTPRODUCTSTATUS"),
    ETS01003("ETS01003", "미선정", "EVENTPRODUCTSTATUS"),

    /*이벤트 상품 접수유형*/
    ETS02001("ETS02001", "사용자 직접접수", "EVENTPRODUCTRCIPPTRN"),
    ETS02002("ETS02002", "관리자 직접접수", "EVENTPRODUCTRCIPPTRN"),

    /*상품 이력 유형타입*/
    HTS00001("HTS00001", "상품 상제 조회", "PRODUCTVIEWSTATUS"),
    HTS00002("HTS00002", "상품 문의 정보 확인", "PRODUCTVIEWSTATUS"),

    /*상품 판매 타입*/
    SELLING_OK("GDS00001", "판매중", "PRODUCTSTATUS"),
    STOP_SALES("GDS00002", "판매중지", "PRODUCTSTATUS"),
    POSTPONE_SALES("GDS00003", "판매보류", "PRODUCTSTATUS"),
    OUT_OF_STOCK("GDS00004", "재고부족", "PRODUCTSTATUS"),
    STOP_SALES_BY_MANAGER("GDS00005", "관리자 판매 중지", "PRODUCTSTATUS"),
    CANCEL_AGENCY_PRODUCT("GDS00006", "에이전시 상품 취소", "PRODUCTSTATUS"),

    /*상품 배송 타입*/
    GDS01001("GDS01001", "국내배송", "DELIVERYTYPE"),
    GDS01002("GDS01002", "해외배송", "DELIVERYTYPE"),
    GDS01003("GDS01003", "국내/해외배송", "DELIVERYTYPE"),

    /*상품 배송 유형*/
    GDS02001("GDS02001", "화물서비스 이용", "DELIVERYPTRN"),
    GDS02002("GDS02002", "직접배송", "DELIVERYPTRN"),
    GDS02003("GDS02003", "무료배송", "DELIVERYPTRN"),
    GDS02004("GDS02004", "구매자수령", "DELIVERYPTRN"),

    GDS03001("GDS03001", "개별상품", "ORDERPTRN"),
    GDS03002("GDS03002", "묶음상품", "ORDERPTRN"),
    GDS03003("GDS03003", "바이어상품", "ORDERPTRN"),
    GDS03004("GDS03004", "견적상품", "ORDERPTRN"),

    /*상품 배송비 유형*/
    GDS04001("GDS04001", "기본 배송비", "DELIVERYPTRN"),
    GDS04002("GDS04002", "지역별 배송비", "DELIVERYPTRN"),
    GDS04003("GDS04003", "수량별 배송비", "DELIVERYPTRN"),
    GDS04004("GDS04004", "지역/수량별 배송비", "DELIVERYPTRN"),
    GDS04005("GDS04005", "화물서비스 견적 배송비", "DELIVERYPTRN"),

    /* 상품에 대한 판매자간 에이전시 처리상태 유형*/
    REQUEST("COC01001", "요청", "STATUSTYPE"),
    STANDBY("COC01002", "대기", "STATUSTYPE"),
    APPROVED("COC01003", "승인", "STATUSTYPE"),
    REJECT("COC01004", "반려", "STATUSTYPE"),
    CANCEL("COC01005", "취소", "STATUSTYPE"),
    APPROVE_CANCEL("COC01006", "승인취소", "STATUSTYPE"),
    CANCEL_REQUEST("COC01007", "취소요청", "STATUSTYPE"),

    /*회원유형*/
    ASSOCIATE_MEMBER("SRS00001", "준회원", "MMBRTYPE"),
    REGULAR_MEMBER("SRS00002", "정회원", "MMBRTYPE"),
    AGENCY_MEMBER("SRS00003", "에이전시", "MMBRTYPE"),
    PERSONAL_MEMBER("SRS00004", "개인회원", "MMBRTYPE"),

    /* 권한 상태 (정상/제한/탈퇴) */
//    SELLER_APPROVED("SRS03001", "정상", "MMBRSTTS"),
//    SELLER_ROLEOFF("SRS03002", "관리자 제한", "MMBRSTTS"),
//    SELLER_QUIT("SRS03003", "탈퇴", "MMBRSTTS"),

    /*승인유형*/
    SELLER_APPROVED("AUA01001", "승인", "MMBRAUTHTYPE"),
    SELLER_ROLEOFF("AUA01002", "제한", "MMBRAUTHTYPE"),
    SELLER_QUIT("AUA01003", "탈퇴", "MMBRAUTHTYPE"),

    /*문의유형*/
    IQS00001("IQS00001", "질응답", "INQRTYPE"),
    IQS00002("IQS00002", "상품 문의", "INQRTYPE"),
    IQS00003("IQS00003", "에이전시 문의", "INQRTYPE"),
    IQS00004("IQS00004", "견적 문의", "INQRTYPE"),

    /*견적상품유형*/
    ESS01001("ESS01001", "상품", "ESSSTATUS"),
    ESS01002("ESS01002", "직접", "ESSSTATUS"),

    /*견적상태*/
    ESS02001("ESS02001", "발송", "ESSSTATUS"),
    ESS02002("ESS02002", "결제", "ESSSTATUS"),
    ESS02003("ESS02003", "취소", "ESSSTATUS"),

    /*분류항목*/
    COC02001("COC02001", "개", "COCOTYPE"),
    COC02002("COC02002", "kg", "COCOTYPE"),
    COC02003("COC02003", "톤", "COCOTYPE"),

    COC03001("COC03001", "원", "COCOTYPE"),
    COC03002("COC03002", "달러", "COCOTYPE"),

    /*제품포장단뒤*/
    DIS00001("DIS00001", "박스", "DISTYPE"),
    DIS00002("DIS00002", "파렛트", "DISTYPE"),

    /*판매자 이미지유형 구분*/
    SELLERIMG_BG_IMG("SRS01001", "배경이미지", "SELLERIMG"),
    SELLERIMG_BANNER_IMG("SRS01002", "배너이미지", "SELLERIMG"),

    /*주문상태*/
    ODS00001("ODS00001", "주문", "ORDERSTATUS"),
    ODS00002("ODS00002", "주문취소 승인", "ORDERSTATUS"),
    ODS00003("ODS00003", "배송중", "ORDERSTATUS"),
    ODS00004("ODS00004", "배송완료", "ORDERSTATUS"),
    ODS00005("ODS00005", "반품요청", "ORDERSTATUS"),
    ODS00006("ODS00006", "반품불가", "ORDERSTATUS"),
    ODS00007("ODS00007", "반품완료", "ORDERSTATUS"),
    ODS00008("ODS00008", "주문취소 완료", "ORDERSTATUS"),

    /*결제상태*/
    PYS01001("PYS01001", "결제대기", "STLMSTATUS"),
    PYS01002("PYS01002", "결제취소", "STLMSTATUS"),
    PYS01003("PYS01003", "결제완료", "STLMSTATUS"),

    /*택배사*/
    ODS01013("ODS01013", "기타(직접입력)", "DELIVERYCMPCODE"),

    /*배송정보 ID*/
    DELIVERY_INFO_DVRY_R("ODS02001", "화물서비스 배송정보", "DELIVERYINFOCODE"),
    DELIVERY_INFO_PDVRY_R("ODS02002", "직접/무료배송", "DELIVERYINFOCODE"), /* 직접배송, 무료배송 */
    DELIVERY_INFO_UDVR_R("ODS02003", "구매자수령", "DELIVERYINFOCODE"); /* 구매자수령 */


    private static final Map<String, String> CODE_MAP = Collections.unmodifiableMap(Stream.of(values()).collect(Collectors.toMap(ComCode::getCode, ComCode::name)));

    private final String code;
    private final String name;
    private final String type;

    ComCode(String code, String name, String type) {
        this.code = code;
        this.name = name;
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public String getName() { return name; }

    public String getType() {
        return type;
    }

    // code 값으로 name 조회
    public static ComCode of(final String code) {
        return ComCode.valueOf(CODE_MAP.get(code));
    }

    // 코드 유효성 확인
    public static boolean check(String code, String type) {
        if (!StringUtils.hasLength(code) || !StringUtils.hasLength(type)) {
            return false;
        }

        List<ComCode> codeList = Arrays.stream(ComCode.values())
                .filter(e -> e.getType().equals(type) && e.getCode().equals(code))
                .collect(Collectors.toList());

        return codeList != null && codeList.size() > 0;
    }

}
