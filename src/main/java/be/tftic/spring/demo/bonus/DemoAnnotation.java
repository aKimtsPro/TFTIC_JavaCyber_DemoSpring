package be.tftic.spring.demo.bonus;

import be.tftic.spring.demo.api.model.dto.UserDTO;

public class DemoAnnotation {

   public static void demoAnnotation(){
       Class<UserDTO> clazz = UserDTO.class;
//		UserDTO dto = new UserDTO(0,null,null);
//		clazz = (Class<UserDTO>) dto.getClass();

		Annotated annotated = clazz.getAnnotation(Annotated.class);
		String valeur = annotated.value();
   }

}
