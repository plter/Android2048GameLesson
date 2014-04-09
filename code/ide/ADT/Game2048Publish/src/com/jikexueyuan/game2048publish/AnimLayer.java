package com.jikexueyuan.game2048publish;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

public class AnimLayer extends FrameLayout {

	public AnimLayer(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initLayer();
	}

	public AnimLayer(Context context, AttributeSet attrs) {
		super(context, attrs);
		initLayer();
	}

	public AnimLayer(Context context) {
		super(context);
		initLayer();
	}
	
	private void initLayer(){
	}
	
	public void createMoveAnim(final Card from,final Card to,int fromX,int toX,int fromY,int toY){
		
		final Card c = getCard(from.getNum());
		
		LayoutParams lp = new LayoutParams(Config.CARD_WIDTH, Config.CARD_WIDTH);
		lp.leftMargin = fromX*Config.CARD_WIDTH;
		lp.topMargin = fromY*Config.CARD_WIDTH;
		c.setLayoutParams(lp);
		
		if (to.getNum()<=0) {
			to.getLabel().setVisibility(View.INVISIBLE);
		}
		TranslateAnimation ta = new TranslateAnimation(0, Config.CARD_WIDTH*(toX-fromX), 0, Config.CARD_WIDTH*(toY-fromY));
		ta.setDuration(100);
		ta.setAnimationListener(new Animation.AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {}
			
			@Override
			public void onAnimationRepeat(Animation animation) {}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				to.getLabel().setVisibility(View.VISIBLE);
				recycleCard(c);
			}
		});
		c.startAnimation(ta);
	}
	
	private Card getCard(int num){
		Card c;
		if (cards.size()>0) {
			c = cards.remove(0);
		}else{
			c = new Card(getContext());
			addView(c);
		}
		c.setVisibility(View.VISIBLE);
		c.setNum(num);
		return c;
	}
	private void recycleCard(Card c){
		c.setVisibility(View.INVISIBLE);
		c.setAnimation(null);
		cards.add(c);
	}
	private List<Card> cards = new ArrayList<Card>();
	
	public void createScaleTo1(Card target){
		ScaleAnimation sa = new ScaleAnimation(0.1f, 1, 0.1f, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		sa.setDuration(100);
		target.setAnimation(null);
		target.getLabel().startAnimation(sa);
	}
	
}
