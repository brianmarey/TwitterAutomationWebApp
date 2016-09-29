package com.careydevelopment.twitterautomation.controller;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.mockito.Mockito.doNothing;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.View;

import com.careydevelopment.twitterautomation.jpa.entity.Project;
import com.careydevelopment.twitterautomation.jpa.entity.ProjectUrl;
import com.careydevelopment.twitterautomation.jpa.entity.TwitterUser;
import com.careydevelopment.twitterautomation.jpa.repository.ProjectRepository;
import com.careydevelopment.twitterautomation.jpa.repository.ProjectUrlRepository;
import com.careydevelopment.twitterautomation.service.UrlMetricsService;
import com.careydevelopment.twitterautomation.util.Constants;

@WebAppConfiguration
@RunWith(MockitoJUnitRunner.class)
public class EditProjectControllerTest {

	@InjectMocks
	EditProjectController controller;

	MockMvc mockMvc;

	@Mock
	View mockView;
	
	@Mock
	ProjectRepository projectRepository;

	
	@Before
	public void setup() throws Exception {
		//MockitoAnnotations.initMocks(this);
		//mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		mockMvc = MockMvcBuilders.standaloneSetup(controller).setSingleView(mockView).build();
	}

	
	@Test
	public void testEditProjectGetWin() {
//		TwitterUser owner = ControllerHelper.getBasicUser();
//
//		try {
//			Project project = new Project();
//			project.setId(1l);
//			project.setName("name");
//			project.setOwner(owner);
//
//			when(projectRepository.findOne(1l)).thenReturn(project);
//			
//			mockMvc.perform(get("/editProject")
//					.sessionAttr(Constants.TWITTER_ENTITY, owner)
//					.param("projectId", "1")
//					.accept(MediaType.TEXT_PLAIN))
//					.andExpect(status().is(200))
//					.andExpect(view().name("editProject"))
//					.andExpect(model().attribute("project",hasProperty("id",is (1l))));
//		} catch (Exception e) {
//			e.printStackTrace();
//			Assert.fail();
//		}
	}

	
//	@Test
//	public void testEditProjectGetNullProject() {
//		TwitterUser owner = ControllerHelper.getBasicUser();
//
//		try {
//			when(projectRepository.findOne(1l)).thenReturn(null);
//			
//			mockMvc.perform(get("/editProject")
//					.sessionAttr(Constants.TWITTER_ENTITY, owner)
//					.param("projectId", "1")
//					.accept(MediaType.TEXT_PLAIN))
//					.andExpect(status().is(200))
//					.andExpect(view().name("redirect:notAuthorized"));
//		} catch (Exception e) {
//			e.printStackTrace();
//			Assert.fail();
//		}
//	}
//
//	
//	@Test
//	public void testEditProjectGetWrongProject() {
//		TwitterUser owner = ControllerHelper.getBasicUser();
//		TwitterUser other = ControllerHelper.getNoRolesUser();
//
//		try {
//			Project project = new Project();
//			project.setId(1l);
//			project.setName("name");
//			project.setOwner(other);
//
//			when(projectRepository.findOne(1l)).thenReturn(project);
//			
//			mockMvc.perform(get("/editProject")
//					.sessionAttr(Constants.TWITTER_ENTITY, owner)
//					.param("projectId", "1")
//					.accept(MediaType.TEXT_PLAIN))
//					.andExpect(status().is(200))
//					.andExpect(view().name("redirect:notAuthorized"));
//		} catch (Exception e) {
//			e.printStackTrace();
//			Assert.fail();
//		}
//	}
//
//	
//	
//	@Test
//	public void testEditProjectGetNotAuthorized() {
//		TwitterUser owner = ControllerHelper.getNoRolesUser();
//
//		try {
//			Project project = new Project();
//			project.setId(1l);
//			project.setName("name");
//			project.setOwner(owner);
//
//			when(projectRepository.findOne(1l)).thenReturn(project);
//			
//			mockMvc.perform(get("/editProject")
//					.sessionAttr(Constants.TWITTER_ENTITY, owner)
//					.param("projectId", "1")
//					.accept(MediaType.TEXT_PLAIN))
//					.andExpect(status().is(200))
//					.andExpect(view().name("redirect:notAuthorized"));
//		} catch (Exception e) {
//			e.printStackTrace();
//			Assert.fail();
//		}
//	}
//	
//	
//	@Test
//	public void testEditProjectGetNotLoggedIn() {
//		TwitterUser owner = ControllerHelper.getBasicUser();
//
//		try {
//			Project project = new Project();
//			project.setId(1l);
//			project.setName("name");
//			project.setOwner(owner);
//
//			when(projectRepository.findOne(1l)).thenReturn(project);
//			
//			mockMvc.perform(get("/editProject")
//					.param("projectId", "1")
//					.accept(MediaType.TEXT_PLAIN))
//					.andExpect(status().is(200))
//					.andExpect(view().name("redirect:notLoggedIn"));
//		} catch (Exception e) {
//			e.printStackTrace();
//			Assert.fail();
//		}
//	}
//
//	
//	@Test
//	public void testEditProjectPostWin() {
//		TwitterUser owner = ControllerHelper.getBasicUser();
//		
//		try {
//			Project project = new Project();
//			project.setId(1l);
//			project.setName("name");
//			project.setOwner(owner);
//						
//			when(projectRepository.findOne(1l)).thenReturn(project);
//			when(projectRepository.save(any(Project.class))).thenReturn(project);
//			
//			mockMvc.perform(post("/editProject")
//					.sessionAttr(Constants.TWITTER_ENTITY, owner)
//					.param("id", "1")
//					.param("name", "name2")
//					.param("status", "Active")
//					.accept(MediaType.TEXT_PLAIN))
//					.andExpect(status().is(200))
//					.andExpect(view().name("redirect:/seoplayhouse"));
//		} catch (Exception e) {
//			e.printStackTrace();
//			Assert.fail();
//		}
//	}
//
//
//	@Test
//	public void testEditProjectPostInvalidName() {
//		TwitterUser owner = ControllerHelper.getBasicUser();
//		
//		try {
//			Project project = new Project();
//			project.setId(1l);
//			project.setName("name");
//			project.setOwner(owner);
//						
//			when(projectRepository.findOne(1l)).thenReturn(project);
//			when(projectRepository.save(any(Project.class))).thenReturn(project);
//			
//			mockMvc.perform(post("/editProject")
//					.sessionAttr(Constants.TWITTER_ENTITY, owner)
//					.param("id", "1")
//					.param("name", "n")
//					.param("status", "Active")
//					.accept(MediaType.TEXT_PLAIN))
//					.andExpect(status().is(200))
//					.andExpect(view().name("editProject"));
//		} catch (Exception e) {
//			e.printStackTrace();
//			Assert.fail();
//		}
//	}
//
//	
//	
//	@Test
//	public void testEditProjectPost() {
//		TwitterUser owner = ControllerHelper.getNoRolesUser();
//		
//		try {
//			Project project = new Project();
//			project.setId(1l);
//			project.setName("name");
//			project.setOwner(owner);
//						
//			when(projectRepository.findOne(1l)).thenReturn(project);
//			when(projectRepository.save(any(Project.class))).thenReturn(project);
//			
//			mockMvc.perform(post("/editProject")
//					.sessionAttr(Constants.TWITTER_ENTITY, owner)
//					.param("id", "1")
//					.param("name", "name2")
//					.param("status", "Active")
//					.accept(MediaType.TEXT_PLAIN))
//					.andExpect(status().is(200))
//					.andExpect(view().name("redirect:notAuthorized"));
//		} catch (Exception e) {
//			e.printStackTrace();
//			Assert.fail();
//		}
//	}
//
//
//	@Test
//	public void testEditProjectPostNotLoggedIn() {
//		TwitterUser owner = ControllerHelper.getBasicUser();
//		
//		try {
//			Project project = new Project();
//			project.setId(1l);
//			project.setName("name");
//			project.setOwner(owner);
//						
//			when(projectRepository.findOne(1l)).thenReturn(project);
//			when(projectRepository.save(any(Project.class))).thenReturn(project);
//			
//			mockMvc.perform(post("/editProject")
//					.param("id", "1")
//					.param("name", "name2")
//					.param("status", "Active")
//					.accept(MediaType.TEXT_PLAIN))
//					.andExpect(status().is(200))
//					.andExpect(view().name("redirect:notLoggedIn"));
//		} catch (Exception e) {
//			e.printStackTrace();
//			Assert.fail();
//		}
//	}
	
}
