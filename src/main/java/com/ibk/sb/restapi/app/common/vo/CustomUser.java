package com.ibk.sb.restapi.app.common.vo;

import com.ibk.sb.restapi.app.common.constant.StatusCode;
import com.ibk.sb.restapi.app.common.util.BizException;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.*;

/**
 * 로그인한 유저 관리용 CustomUser.class
 * 기존 Spring Security User 클래스 참조
 */
public class CustomUser implements UserDetails, Serializable, CredentialsContainer {

    private static final long serialVersionUID = 7726060156164779462L;

    // UserDetails 기본 멤버
    private final String loginUserId;
    private final String utlinsttId;

    private String password;
    private final Set<GrantedAuthority> authorities;
    private final boolean accountNonExpired;
    private final boolean accountNonLocked;
    private final boolean credentialsNonExpired;
    private final boolean enabled;

    // 회원 분류(준회원, 정회원, 에이전시)
    private final String userGroupType;

    // 가입 회원의 사업자 번호
    private final String bizrno;

    /** CustomUser 생성자 **/
    public CustomUser(
            String loginUserId, String utlinsttId, String password, Collection<? extends GrantedAuthority> authorities, String userGroupType, String bizrno) {

        if(!StringUtils.hasLength(loginUserId) || !StringUtils.hasLength(utlinsttId)) {
            throw new BizException(StatusCode.COM0001);
        }

        this.loginUserId = loginUserId;
        this.utlinsttId = utlinsttId;
        this.password = password;
        this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities)); // 변경불가 Collection 처리

        // 회원 정보
        this.userGroupType = userGroupType;
        // 사업자 번호
        this.bizrno = bizrno;

        // 필요시 accountNonExpired 등 기본 멤버 세팅하는 생성자 추가
        this.enabled = true;
        this.accountNonExpired = true;
        this.credentialsNonExpired = true;
        this.accountNonLocked = true;
    }

    // Set Sort + Set Null 체크
    public static SortedSet<GrantedAuthority> sortAuthorities(Collection<? extends GrantedAuthority> authorities) {
        if (authorities == null) {
            throw new BizException(StatusCode.COM0001);
        }
        SortedSet<GrantedAuthority> sortedAuthorities = new TreeSet<>(new CustomUser.AuthorityComparator());
        Iterator iterator = authorities.iterator();

        while (iterator.hasNext()) {
            GrantedAuthority grantedAuthority = (GrantedAuthority) iterator.next();
            if (grantedAuthority == null) {
                throw new BizException(StatusCode.COM0001);
            }
            sortedAuthorities.add(grantedAuthority);
        }

        return sortedAuthorities;
    }

    // 기존 User 클래스 기준 : username
    public boolean equals(Object object) {
        if(object instanceof CustomUser) {
            return this.loginUserId.equals(((CustomUser)object).getUsername());
        } else {
            return false;
        }
    }

    // 기존 User 클래스 기준 : username
    public int hashCode() { return this.loginUserId.hashCode(); }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getName()).append(" [");
        sb.append("loginUserId=").append(this.loginUserId).append(", ");
        sb.append("utlinsttId=").append(this.utlinsttId).append(", ");
        sb.append("Password=[PROTECTED], ");
        sb.append("Enabled=").append(this.enabled).append(", ");
        sb.append("AccountNonExpired=").append(this.accountNonExpired).append(", ");
        sb.append("AccountNonLocked=").append(this.accountNonLocked).append(", ");
        sb.append("CredentialsNonExpired=").append(this.credentialsNonExpired).append(", ");
        sb.append("Granted Authorities=").append(this.authorities).append(", ");
        sb.append("UserGroupType=").append(this.userGroupType).append(", ");
        sb.append("bizrno=").append(this.bizrno).append("]");

        return sb.toString();
    }

    @Override
    public void eraseCredentials() {
        this.password = null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.loginUserId;
    }

    public String getLoginUserId() {
        return this.loginUserId;
    }

    public String getUtlinsttId() { return this.utlinsttId; }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public String getUserGroupType() {
        return this.userGroupType;
    }

    public String getBizrno() {return this.bizrno;}

    // Set element Null 체크 + compare 정의
    private static class AuthorityComparator implements Comparator<GrantedAuthority>, Serializable {
        private static final long serialVersionUID = -8512715648856716422L;

        private AuthorityComparator() {}

        public int compare(GrantedAuthority g1, GrantedAuthority g2) {
            if(g2.getAuthority() == null) {
                return -1;
            } else {
                return g1.getAuthority() == null ? 1 : g1.getAuthority().compareTo(g2.getAuthority());
            }
        }
    }
}
