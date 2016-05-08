package com.careydevelopment.twitterautomation.util;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.careydevelopment.twitterautomation.domain.Tip;

import twitter4j.User;

public class TipsHelper {
	private static final Logger LOGGER = LoggerFactory.getLogger(TipsHelper.class);
	
	private User user;
	
	public TipsHelper(User user) {
		this.user = user;
	}
	
	public List<Tip> getTips() {
		List<Tip> tips = new ArrayList<Tip>();
		
		if (user.getFollowersCount() < 2000) {
			tips.add(getLessThan2000FollowersTip());
		}
		
		double ratio = ((double)user.getFollowersCount())/((double)user.getFriendsCount());
		if (ratio < .7) {
			tips.add(getRatioTooHighTip());
		}
		
		return tips;
	}
	
	
	private Tip getRatioTooHighTip() {
		Tip tip = new Tip();
		tip.setTitle("Improve Your Ratio");
		tip.setDescription("You're following too many people in relation to how many people are following you. "
				+ "Try to get some more followers or unfollow people who aren't following you back.");
		
		return tip;
	}
	
	
	private Tip getLessThan2000FollowersTip() {
		Tip tip = new Tip();
		tip.setTitle("Climb Above \"The 2,000 Threshold\"");
		tip.setDescription("You have less than 2,000 followers. To take full advantage of BlastFollow, you should have "
				+ "at least 2,000 followers. Get some more followers by following people who are likely to follow you back. "
				+ "Also, consider using another tool like AddMeFast.");

		return tip;
	}
}
