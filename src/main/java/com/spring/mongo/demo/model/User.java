package com.spring.mongo.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import java.util.List;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements Serializable {

	@Id
	private String id;
	private String refId;
	private String name;
	private String investment;
	private String profit;
	private long connection;
	private List<Income> incomeList;


}
