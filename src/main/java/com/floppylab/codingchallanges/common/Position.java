package com.floppylab.codingchallanges.common;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"x", "y"})
public class Position implements Serializable {

	private static final long serialVersionUID = 0L;

	@NotNull
	private Integer x;

	@NotNull
	private Integer y;

}


