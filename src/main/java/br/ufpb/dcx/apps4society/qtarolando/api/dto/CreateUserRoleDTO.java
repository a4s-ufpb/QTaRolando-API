package br.ufpb.dcx.apps4society.qtarolando.api.dto;

import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class CreateUserRoleDTO {

  private UUID userId;

  private List<Integer> idsRoles;

}