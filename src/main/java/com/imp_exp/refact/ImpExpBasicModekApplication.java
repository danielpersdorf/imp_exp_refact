package com.imp_exp.refact;

import com.imp_exp.refact.basicModel.import_export;
import com.imp_exp.refact.tinyErpModel.BusinessService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;


@SpringBootApplication
public class ImpExpBasicModekApplication {

	public static BusinessService business;
	public static import_export imp_exp;

	public static void main(String[] args) throws IOException {
		SpringApplication.run(ImpExpBasicModekApplication.class, args);

		// starting business
		business = new BusinessService();

		// starting imp_exp
		import_export.main(new String[] {"none"});

	}

}
