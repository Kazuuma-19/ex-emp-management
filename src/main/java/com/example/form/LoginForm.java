package com.example.form;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 管理者としてログインするためのフォーム
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class LoginForm {
    private String mailAddress;
    private String password;
}
