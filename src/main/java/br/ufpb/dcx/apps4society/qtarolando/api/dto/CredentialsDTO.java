package br.ufpb.dcx.apps4society.qtarolando.api.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CredentialsDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
