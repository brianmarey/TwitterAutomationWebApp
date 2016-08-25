package com.careydevelopment.twitterautomation.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.mockito.Matchers.any;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
import org.springframework.ui.Model;
import org.springframework.web.servlet.View;

import com.careydevelopment.twitterautomation.jpa.entity.Project;
import com.careydevelopment.twitterautomation.jpa.entity.ProjectUrl;
import com.careydevelopment.twitterautomation.jpa.entity.TwitterUser;
import com.careydevelopment.twitterautomation.jpa.repository.ProjectRepository;
import com.careydevelopment.twitterautomation.jpa.repository.ProjectUrlRepository;
import com.careydevelopment.twitterautomation.util.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebAppConfiguration
@RunWith(MockitoJUnitRunner.class)
public class CreateProjectUrlControllerTest {

	@InjectMocks
	CreateProjectUrlController controller;

	MockMvc mockMvc;

	@Mock
	View mockView;
	
	@Mock
	ProjectRepository projectRepository;

	@Mock
	ProjectUrlRepository projectUrlRepository;

	
	@Before
	public void setup() throws Exception {
		//MockitoAnnotations.initMocks(this);
		//mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		mockMvc = MockMvcBuilders.standaloneSetup(controller).setSingleView(mockView).build();
	}

	
	@Test
	public void testCreateProjectUrlGetWin() {
		TwitterUser owner = ControllerHelper.getBasicUser();

		try {
			Project project = new Project();
			project.setId(1l);
			project.setName("name");
			project.setOwner(owner);

			when(projectRepository.findOne(1l)).thenReturn(project);

			List<ProjectUrl> projectUrls = new ArrayList<ProjectUrl>();
			
			when(projectUrlRepository.findByProject(project)).thenReturn(projectUrls);
			
			mockMvc.perform(get("/createProjectUrl")
					.sessionAttr(Constants.TWITTER_ENTITY, owner)
					.param("projectId", "1")
					.accept(MediaType.TEXT_PLAIN))
					.andExpect(status().is(200))
					.andExpect(view().name("createProjectUrl"))
					.andExpect(model().attribute("project",hasProperty("id",is (1l))));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	
	@Test
	public void testCreateProjectUrlPostWin() {
		TwitterUser owner = ControllerHelper.getBasicUser();
		
		try {
			Project project = new Project();
			project.setId(1l);
			project.setName("name");
			project.setOwner(owner);
			
			List<ProjectUrl> projectUrls = new ArrayList<ProjectUrl>();

			when(projectRepository.findOne(1l)).thenReturn(project);			
			when(projectUrlRepository.findByProject(project)).thenReturn(projectUrls);
			when(projectUrlRepository.save(any(ProjectUrl.class))).thenReturn(new ProjectUrl());
			
			mockMvc.perform(post("/createProjectUrl")
					.sessionAttr(Constants.TWITTER_ENTITY, owner)
					.param("projectId", "1")
					.param("url", "hotair.com")
					.accept(MediaType.TEXT_PLAIN))
					.andExpect(status().is(200))
					.andExpect(view().name("redirect:/projectView?projectId=1"));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}


	@Test
	public void testCreateProjectUrlPostTooShortUrl() {
		TwitterUser owner = ControllerHelper.getBasicUser();
		
		try {
			Project project = new Project();
			project.setId(1l);
			project.setName("name");
			project.setOwner(owner);

			when(projectRepository.findOne(1l)).thenReturn(project);

			List<ProjectUrl> projectUrls = new ArrayList<ProjectUrl>();
			
			when(projectUrlRepository.findByProject(project)).thenReturn(projectUrls);
			
			mockMvc.perform(post("/createProjectUrl")
					.sessionAttr(Constants.TWITTER_ENTITY, owner)
					.param("projectId", "1")
					.param("url", "co")
					.accept(MediaType.TEXT_PLAIN))
					.andExpect(status().is(200))
					.andExpect(view().name("createProjectUrl"));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	
	
	@Test
	public void testCreateProjectUrlPostBadUrl() {
		TwitterUser owner = ControllerHelper.getBasicUser();
		
		try {
			Project project = new Project();
			project.setId(1l);
			project.setName("name");
			project.setOwner(owner);

			when(projectRepository.findOne(1l)).thenReturn(project);

			List<ProjectUrl> projectUrls = new ArrayList<ProjectUrl>();
			
			when(projectUrlRepository.findByProject(project)).thenReturn(projectUrls);
			
			mockMvc.perform(post("/createProjectUrl")
					.sessionAttr(Constants.TWITTER_ENTITY, owner)
					.param("projectId", "1")
					.param("url", "sdfsdfsdf")
					.accept(MediaType.TEXT_PLAIN))
					.andExpect(status().is(200))
					.andExpect(view().name("createProjectUrl"))
					.andExpect(model().attribute("invalidUrl",is(true)));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	
	
	@Test
	public void testCreateProjectUrlPostTooManyUrls() {
		TwitterUser owner = ControllerHelper.getBasicUser();
		
		try {
			Project project = new Project();
			project.setId(1l);
			project.setName("name");
			project.setOwner(owner);

			when(projectRepository.findOne(1l)).thenReturn(project);

			List<ProjectUrl> projectUrls = new ArrayList<ProjectUrl>();
			for (int i=0;i<=4;i++) {
				ProjectUrl p = new ProjectUrl();
				projectUrls.add(p);
			}
			
			when(projectUrlRepository.findByProject(project)).thenReturn(projectUrls);
			
			mockMvc.perform(post("/createProjectUrl")
					.sessionAttr(Constants.TWITTER_ENTITY, owner)
					.param("projectId", "1")
					.param("url", "hotair.com")
					.accept(MediaType.TEXT_PLAIN))
					.andExpect(status().is(200))
					.andExpect(view().name("createProjectUrl"))
					.andExpect(model().attribute("project",hasProperty("id",is (1l))))
					.andExpect(model().attribute("maxUrlsExceeded",is(true)))
					.andExpect(model().attribute("maxUrls",is(owner.getUserConfig().getMaxUrlsPerProject())));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

		
	@Test
	public void testCreateProjectUrlPostWrongProject() {
		TwitterUser owner = ControllerHelper.getBasicUser();
		TwitterUser other = ControllerHelper.getNoRolesUser();
		
		try {
			Project project = new Project();
			project.setId(1l);
			project.setName("name");
			project.setOwner(other);

			when(projectRepository.findOne(1l)).thenReturn(project);

			List<ProjectUrl> projectUrls = new ArrayList<ProjectUrl>();
			
			when(projectUrlRepository.findByProject(project)).thenReturn(projectUrls);
			
			mockMvc.perform(post("/createProjectUrl")
					.sessionAttr(Constants.TWITTER_ENTITY, owner)
					.param("projectId", "1")
					.param("url", "hotair.com")
					.accept(MediaType.TEXT_PLAIN))
					.andExpect(status().is(200))
					.andExpect(view().name(ControllerHelper.NOT_AUTHORIZED));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	
	
	@Test
	public void testCreateProjectUrlPostNotAuthorized() {
		TwitterUser owner = ControllerHelper.getNoRolesUser();
		
		try {
			Project project = new Project();
			project.setId(1l);
			project.setName("name");
			project.setOwner(owner);

			when(projectRepository.findOne(1l)).thenReturn(project);

			List<ProjectUrl> projectUrls = new ArrayList<ProjectUrl>();
			
			when(projectUrlRepository.findByProject(project)).thenReturn(projectUrls);
			
			mockMvc.perform(post("/createProjectUrl")
					.sessionAttr(Constants.TWITTER_ENTITY, owner)
					.param("projectId", "1")
					.param("url", "hotair.com")
					.accept(MediaType.TEXT_PLAIN))
					.andExpect(status().is(200))
					.andExpect(view().name(ControllerHelper.NOT_AUTHORIZED));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	
	@Test
	public void testCreateProjectUrlPostNotLoggedIn() {
		TwitterUser owner = ControllerHelper.getBasicUser();
		
		try {
			Project project = new Project();
			project.setId(1l);
			project.setName("name");
			project.setOwner(owner);

			when(projectRepository.findOne(1l)).thenReturn(project);

			List<ProjectUrl> projectUrls = new ArrayList<ProjectUrl>();
			
			when(projectUrlRepository.findByProject(project)).thenReturn(projectUrls);
			
			mockMvc.perform(post("/createProjectUrl")
					.param("projectId", "1")
					.param("url", "hotair.com")
					.accept(MediaType.TEXT_PLAIN))
					.andExpect(status().is(200))
					.andExpect(view().name(ControllerHelper.NOT_LOGGED_IN));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	

	@Test
	public void testCreateProjectUrlGetWrongProject() {
		TwitterUser owner = ControllerHelper.getBasicUser();
		TwitterUser other = ControllerHelper.getNoRolesUser();

		try {
			Project project = new Project();
			project.setId(1l);
			project.setName("name");
			project.setOwner(other);

			when(projectRepository.findOne(1l)).thenReturn(project);

			List<ProjectUrl> projectUrls = new ArrayList<ProjectUrl>();
			
			when(projectUrlRepository.findByProject(project)).thenReturn(projectUrls);
			
			mockMvc.perform(get("/createProjectUrl")
					.sessionAttr(Constants.TWITTER_ENTITY, owner)
					.param("projectId", "1")
					.accept(MediaType.TEXT_PLAIN))
					.andExpect(status().is(200))
					.andExpect(view().name(ControllerHelper.NOT_AUTHORIZED));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	
	
	@Test
	public void testCreateProjectUrlGetNotLoggedIn() {
		TwitterUser owner = ControllerHelper.getBasicUser();

		try {
			Project project = new Project();
			project.setId(1l);
			project.setName("name");
			project.setOwner(owner);

			when(projectRepository.findOne(1l)).thenReturn(project);

			List<ProjectUrl> projectUrls = new ArrayList<ProjectUrl>();
			
			when(projectUrlRepository.findByProject(project)).thenReturn(projectUrls);
			
			mockMvc.perform(get("/createProjectUrl")
					.param("projectId", "1")
					.accept(MediaType.TEXT_PLAIN))
					.andExpect(status().is(200))
					.andExpect(view().name(ControllerHelper.NOT_LOGGED_IN));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	
	@Test
	public void testCreateProjectUrlGetNotAuthorized() {
		TwitterUser owner = ControllerHelper.getNoRolesUser();

		try {
			Project project = new Project();
			project.setId(1l);
			project.setName("name");
			project.setOwner(owner);

			when(projectRepository.findOne(1l)).thenReturn(project);

			List<ProjectUrl> projectUrls = new ArrayList<ProjectUrl>();
			
			when(projectUrlRepository.findByProject(project)).thenReturn(projectUrls);
			
			mockMvc.perform(get("/createProjectUrl")
					.sessionAttr(Constants.TWITTER_ENTITY, owner)
					.param("projectId", "1")
					.accept(MediaType.TEXT_PLAIN))
					.andExpect(status().is(200))
					.andExpect(view().name(ControllerHelper.NOT_AUTHORIZED));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
}
