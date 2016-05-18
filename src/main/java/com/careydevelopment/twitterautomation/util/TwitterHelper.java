package com.careydevelopment.twitterautomation.util;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.Friendship;
import twitter4j.PagableResponseList;
import twitter4j.ResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

public class TwitterHelper {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TwitterHelper.class);
	
	private static final int MAX_PER_PAGE = 200;
	private static final int DEFAULT_SLEEP_TIME = 3000;

	public static void getFollowers(Twitter twitter, List<User> users, String screenName, int iterations) throws TwitterException, InterruptedException {
		long cursor = -1;
		
		for (int i=0;i<iterations;i++) {
			PagableResponseList<User> tweeps = twitter.getFollowersList(screenName, cursor, MAX_PER_PAGE);
			
			for (User user : tweeps) {
				users.add(user);
			}

			cursor = tweeps.getNextCursor();
			if (cursor == 0) break;
			Thread.sleep(DEFAULT_SLEEP_TIME);
		}
	}
	
	
	public static List<String> getFriendshipsFromUsers(List<User> users, Twitter twitter) throws TwitterException, InterruptedException {
		List<String> finalNames = new ArrayList<String>();		
		
		int start = 0;
		int length = 100;
		while (finalNames.size() < Constants.MAX_FOLLOW_SIZE && start < users.size() - (length + 1)) {
			long[] subIds = new long[length];
			int index = 0;
			
			for (int i=start;i<start+length;i++) {
				User user = users.get(i);
				long id = user.getId();
				subIds[index] = id;
				index++;
			}
			
			ResponseList<Friendship> friendships = twitter.lookupFriendships(subIds);
			
			for (Friendship friendship : friendships) {
				//LOGGER.info(friendship.getScreenName() + " " + friendship.isFollowedBy() + " " + friendship.isFollowing());
				if (!friendship.isFollowedBy()) {
					//LOGGER.info("Now adding " + friendship.getScreenName());
					finalNames.add(friendship.getScreenName());
				}
			}
			
			start += length;
			Thread.sleep(3000);
		}

		return finalNames;
	}
}
