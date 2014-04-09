import java.io.IOException;

/**
 * We use a driver class BatterBotDriver which contains all of the components
 * needed for the Chatter Bot. This class also stores the main function cycle()
 * which iterates through the user input and chatter bot response cycle.
 * Additionally, there is a method setup(), which can be used to load our sample
 * Batter Bot Response Template files.
 * 
 * @author Yasha
 * @author Benjamin
 * 
 */
public class BatterBotDriver 
{
	private IOInterface IO = null;
	private LanguageProcessorInterface LP = new LanguageProcessor();
	private ResponseSelectorInterface RS = new ResponseSelector();
	private ResponseBuilderInterface RB = new ResponseBuilder();
	//This way, the YAnswers! implementation makes sense to have.
	private boolean ansMode = false;
	
	
	
	/**
	 * Basic constructor which sets up the IO class.
	 * All other necessary classes should be set to default (needs implementation still).
	 * 
	 * @param IO The class that will handle the IO operations
	 */
	public BatterBotDriver(IOInterface IO)
	{
		this.IO = IO;
	}
	
	
	/**
	 * The main function that can be called to interact with the batterbot. This function
	 * handles all of the IO operations, message decoding, response building, and will
	 * terminate naturally when the batterbot decides that conversation is over and sends
	 * a valediction.
	 * @throws IOException 
	 */
	public void cycle() throws IOException
	{
		String input;
		ResponseTemplate template = null;
		
		while(true)
		{
			//This is expected to block waiting for input, but it doesn't because I don't understand how to semaphore.
			input = IO.read();
			//Need to declare this up here
			String response = null;
			
			/**
			 * Need to do different things depending on what mode the bot is in.
			 * Starting mode is, of course, Batman.
			 * If you say a certain line, 'I have a question' (or the like), the bot's mode will switch
			 * and it will redirect the things you say to YAnswers.
			 * 
			 * Otherwise, it will take the normal path and use the language processors, etc.
			 */
			//The past is over, John; now for something new and improved!
			if (ansMode == true)
			{
				//Just check exit code directly
				boolean containsQ = input.contains("quit");
				boolean containsE = input.contains("exit");
				//TODO: Add valediction support if the user says 'Bye'.
				//TODO: Handle exceptions (if the system can't return anything).
				
				if (containsQ || containsE)
				{
					ansMode = false;
					response = "Ok, let's get back to the matter at hand.";
					//Or could do this with a template
				}
				else
				{
					response = RB.yAnswer(input) + "\nAnything else?";
				}
			}

			else	//Following the main track
			{
				//Decode the user input into keywords
				KeyWordList keys = LP.extractKeyWords(input);
						
				if(template != null)
					template.processResponse(input, keys);
						
				//Select the next response template
				template = RS.selectTemplate(keys);
						
				if(template.needsWiki)
				{
					response = RB.fromWiki(template);
				}
				else if(template.answerTrigger)
				{
					ansMode = true;
					response = "You put me into Question Mode.  What's up?";
				}
				else  //OR	is it?
				{
					response = RB.buildResponse(template, keys);
				}
			}

			
			//Old block
			/*
			//Decode the user input into keywords
			KeyWordList keys = LP.extractKeyWords(input);
			
			if(template != null)
				template.processResponse(input, keys);
			
			//Select the next response template
			template = RS.selectTemplate(keys);
			
			String response = null;
			
			
			//TODO: Let the system append a witty response after getting from the Wiki.
			if(template.needsWiki)
			{
				response = RB.fromWiki(template);
			}
			
			//If the response triggers the YAnswers mode:
			else if(template.answerTrigger)
			{
				ansMode = true;
			}
			
			//Build the response from template as normal; this will happen at all times.
			//The bot should provide a commentary on the Wikipedia entry though it needn't be dynamic.
			response = RB.buildResponse(template, keys);
			*/  //END OF OLD BLOCK 
			
			//Print the next response
			IO.print(response);
			
			//Checks if the response ends the conversation.
			if(template.isValediction())
				break;
			
			//Sleep for 3 seconds.
			try
    		{
	    		//Wait 3 seconds.  This is a bad hack- will it withstand the listener?
	    		Thread.sleep(3000);
    		}
    		catch(InterruptedException ex)
    		{
    			Thread.currentThread().interrupt();
    		}
		}
	}
	
	
	public void setup()
	{
		//Response templates declared here.
		
				String[] sentence0 = {"I'm sorry, I don't understand....my time is precious. We could talk about"};
				String[][] bucket0 = {{" Superman"," Wonder Woman"," The Justice League"," Green Lantern"," The Flash"," my enemies"}};
				String[] keys0 = {"null"};
		
				ResponseTemplate response0 = new ResponseTemplate(sentence0, bucket0, keys0);
				
				String[] sentence = {"Hi, this is Batman. Ask me about crime fighting..."};
				String[][] bucket = null;
				String[] keys = {"Hello", "Hi", "Hey"};
				
				ResponseTemplate response1 = new ResponseTemplate(sentence, bucket, keys);
						
				String[] sentence2 = {"I don't care either..."};
				String[][] bucket2 = null;
				String[] keys2 = {"i don't care","i dont care"};
				
				ResponseTemplate response2 = new ResponseTemplate(sentence2, bucket2, keys2);
				
				String[] sentence3 = {"Robin is the Boy Wonder, and my sidekick"};
				String[][] bucket3 = null;
				String[] keys3 = {"who is robin"};
				
				ResponseTemplate response3 = new ResponseTemplate(sentence3, bucket3, keys3);
				
				String[] sentence4 = {"I cannot tell you my true identity.  It protects my friends from danger"};
				String[][] bucket4 = null;
				String[] keys4 = {"true","identity","who are you"};
				
				ResponseTemplate response4 = new ResponseTemplate(sentence4, bucket4, keys4);
				
				String[] sentence5 = {"I cannot tell you Robin's true identity, I'll never put him in danger"};
				String[][] bucket5 = null;
				String[] keys5 = {"robin","true","identity"};
				
				ResponseTemplate response5 = new ResponseTemplate(sentence5, bucket5, keys5);
				
				String[] sentence6 = {"No....I've never met Mr. Wayne"};
				String[][] bucket6 = null;
				String[] keys6 = {"Bruce","Wayne"};
				
				ResponseTemplate response6 = new ResponseTemplate(sentence6, bucket6, keys6);
				
				String[] sentence7 = {"Yes. Commissioner Gordon protects Gotham on a daily basis...I rely on his help to fight crime"};
				String[][] bucket7 = null;
				String[] keys7 = {"know gordon","about gordon","think about gordon", "commissioner gordon"};
				
				ResponseTemplate response7 = new ResponseTemplate(sentence7, bucket7, keys7);
				
				String[] sentence8 = {"Of course! Superman and I are bros. We're both in the Justice League."};
				String[][] bucket8 = null;
				String[] keys8 = {"know superman","like superman","think about superman"};
				
				ResponseTemplate response8 = new ResponseTemplate(sentence8, bucket8, keys8);
				
				String[] sentence9 = {"Yes...her and I have always had a 'thing'.  We're both in the Justice League."};
				String[][] bucket9 = null;
				String[] keys9 = {"know wonderwoman","know wonder woman","like wonder woman","like wonderwoman","think about wonderwoman","think about wonder woman"};
				
				ResponseTemplate response9 = new ResponseTemplate(sentence9, bucket9, keys9);
				
				String[] sentence10 = {"Yes. We are both members of the Justice League. He has a really cool ring, I'm a little jealous of it"};
				String[][] bucket10 = null;
				String[] keys10 = {"know green lantern","like green lantern","think about green lantern"};
				
				ResponseTemplate response10 = new ResponseTemplate(sentence10, bucket10, keys10);
				
				String[] sentence11 = {"Yes. We're both members of the Justice League. He's a fool, but he is fast."};
				String[][] bucket11 = null;
				String[] keys11 = {"know flash","like flash"};
				
				ResponseTemplate response11 = new ResponseTemplate(sentence11, bucket11, keys11);
				
				String[] sentence12 = {"The Justice League is a league of heroes sworn to the protection of Earth."};
				String[][] bucket12 = null;
				String[] keys12 = {"what is the justice league","what's the justice league"};
				
				ResponseTemplate response12 = new ResponseTemplate(sentence12, bucket12, keys12);
				
				String[] sentence13 = {"The core members of the Justice League are Myself, Superman, Wonder Woman, Green Lantern, Flash, and the Martian. They are my only friends"};
				String[][] bucket13 = null;
				String[] keys13 = {"in the justice league","in justice league","friends"};
				
				ResponseTemplate response13 = new ResponseTemplate(sentence13, bucket13, keys13);
				
				String[] sentence14 = {"The Green Lantern derives his power from his magical ring, or 'Lantern'. One of these rings is bestowed upon a worthy individual to protect his section of the Galaxy. He protects Earth"};
				String[][] bucket14 = null;
				String[] keys14 = {"about green lantern", "who is green lantern"};
				
				ResponseTemplate response14 = new ResponseTemplate(sentence14, bucket14, keys14);
				
				String[] sentence15 = {"Superman is the last know Kryptonian. He was sent to earth as a child, when his planet Krypton exploded. He values justice above all else and will defend the Earth, his new home.  Also he has so many powers...it's unfair :("};
				String[][] bucket15 = null;
				String[] keys15 = {"about superman","who is superman"};
				
				ResponseTemplate response15 = new ResponseTemplate(sentence15, bucket15, keys15);
				
				String[] sentence16 = {"Wonder Woman is an Amazonian.  She comes from a far away planet full of sexy women ;)"};
				String[][] bucket16 = null;
				String[] keys16 = {"about wonder woman","who is wonder woman"};
				
				ResponseTemplate response16 = new ResponseTemplate(sentence16, bucket16, keys16);
				
				String[] sentence17 = {"The Flash is the fastest man on earth. He can run at supersonic speeds....unfortunately, the only thing faster than him is his mouth."};
				String[][] bucket17 = null;
				String[] keys17 = {"about flash","about the flash","who is flash","who is the flash"};
				
				ResponseTemplate response17 = new ResponseTemplate(sentence17, bucket17, keys17);
				
				String[] sentence18 = {"Seriously...he's a martian with superpowers. I thought that one was pretty self-explanatory"};
				String[][] bucket18 = null;
				String[] keys18 = {"about Martian","about the martian","who is martian","who is the martian"};
				
				ResponseTemplate response18 = new ResponseTemplate(sentence18, bucket18, keys18);
				
				String[] sentence19 = {"Bats are really scary!!! I fell down a well when I was a kid and cried the bats were so scary!"};
				String[][] bucket19 = null;
				String[] keys19 = {"bat"};
				
				ResponseTemplate response19 = new ResponseTemplate(sentence19, bucket19, keys19);
				
				String[] sentence20 = {"I fight crime to avenge the death of my parents, by beating up the bad guys of Gotham! Don't try yourself, you could get hurt"};
				String[][] bucket20 = null;
				String[] keys20 = {"fight crime"};
				
				ResponseTemplate response20 = new ResponseTemplate(sentence20, bucket20, keys20);
				
				String[] sentence21 = {"A bad guy! I don't want to talk about it!!! :'("};
				String[][] bucket21 = null;
				String[] keys21 = {"killed your parents","killed them","they die"};
				
				ResponseTemplate response21 = new ResponseTemplate(sentence21, bucket21, keys21);
				
				String[] sentence22 = {"My parents died when I was  little boy. I watched them be murdered..."};
				String[][] bucket22 = null;
				String[] keys22 = {"parents die","they die?"};
				
				ResponseTemplate response22 = new ResponseTemplate(sentence22, bucket22, keys22);
	
				String[] sentence23 = {"I was trained by the league of shadows."};
				String[][] bucket23 = null;
				String[] keys23 = {"train"};
				
				ResponseTemplate response23 = new ResponseTemplate(sentence23, bucket23, keys23);
				
				String[] sentence24 = {"The League of Shadows is an acient cult. Bent on wiping Gotham off the map.  They are led by Raz Al-Gul"};
				String[][] bucket24 = null;
				String[] keys24 = {"league of shadow"};
				
				ResponseTemplate response24 = new ResponseTemplate(sentence24, bucket24, keys24);
				
				String[] sentence25 = {"Raz Al-Gul is the leader of the League of Shadows, and one of my many enemies. He is thousands of years old. He gets his youth from the legendary Lazarus Pools"};
				String[][] bucket25 = null;
				String[] keys25 = {"raz","al-gul","leader"};
				
				ResponseTemplate response25 = new ResponseTemplate(sentence25, bucket25, keys25);
				
				String[] sentence26 = {"The Lazarus pools are the source of the legend of the fountain of youth. They're one and the same, and Raz Al-Gul is the only person who knows how to use them."};
				String[][] bucket26 = null;
				String[] keys26 = {"lazarus","pools"};
				
				ResponseTemplate response26 = new ResponseTemplate(sentence26, bucket26, keys26);
				
				String[] sentence27 = {"The Joker is a psychopathic clown who just wants to watch the world burn. He is my oldest and most dangerous enemy!"};
				String[][] bucket27 = null;
				String[] keys27 = {"joker"};		
				
				ResponseTemplate response27 = new ResponseTemplate(sentence27, bucket27, keys27);
				//Test for the wiki.
				response27.needsWiki = true;
				response27.wikiTerm = "Joker (comics)";
				
				
				String[] sentence28 = {"Mr. Cobblepot, aka the Penguin, is a deranged, mutated man who blames the rest of the world for his misfortune. We have fought many times."};
				String[][] bucket28 = null;
				String[] keys28 = {"penguin", "cobblepot"};
				
				ResponseTemplate response28 = new ResponseTemplate(sentence28, bucket28, keys28);
				
				String[] sentence29 = {"Born Harvery Dent, a former prosecutor, He developed a psychopathic obsession with duality after...I'd rather not talk about it"};
				String[][] bucket29 = null;
				String[] keys29 = {"two face","two-face"};
				
				ResponseTemplate response29 = new ResponseTemplate(sentence29, bucket29, keys29);
				
				String[] sentence30 = {"I have many enemies. The joker, Raz Al-gul, the Penguin, and Two-Face are the enemies I encounter the most"};
				String[][] bucket30 = null;
				String[] keys30 = {"enemies"};
				
				ResponseTemplate response30 = new ResponseTemplate(sentence30, bucket30, keys30);
				
				String[] sentence31 = {"Where are the other drugs!!"};
				String[][] bucket31 = null;
				String[] keys31 = {"drugs"};
				
				ResponseTemplate response31 = new ResponseTemplate(sentence31, bucket31, keys31);
				
				String[] sentence32 = {"Where are the hostages?"};
				String[][] bucket32 = null;
				String[] keys32 = {"hostage"};
				
				ResponseTemplate response32 = new ResponseTemplate(sentence32, bucket32, keys32);
				
				String[] sentence33 = {"I'll be there as soon as I can!!"};
				String[][] bucket33 = null;
				String[] keys33 = {"rd.","st.","blvd.","hwy.","road","street","boulevard","highway"};
				
				ResponseTemplate response33 = new ResponseTemplate(sentence33, bucket33, keys33);
				
				String[] sentence34 = {"Goodbye, Gotham Citizen"};
				String[][] bucket34 = null;
				String[] keys34 = {"bye"};
				
				ResponseTemplate response34 = new ResponseTemplate(sentence34, bucket34, keys34);
				response34.valediction=true;
				
				String[] sentence35 = {"Busy. I'm investigating a case with",};
				String[][] bucket35 = {{" Superman"," Wonder Woman"," The Flash"," The Green Lantern"," The Justice League"}};
				String[] keys35 = {"How are you","it going","you doing"};
				
				ResponseTemplate response35 = new ResponseTemplate(sentence35, bucket35, keys35);
				
				String[] sentence36 = {"My age is unimportant. I am busy fighting crime with the Justice League"};
				String[][] bucket36 = null;
				String[] keys36 = {"your age","old are you"};
				
				ResponseTemplate response36 = new ResponseTemplate(sentence36, bucket36, keys36);
				
				String[] sentence37 = {"I live in Gotham City! Meteropolis is a Superman's city."};
				String[][] bucket37 = null;
				String[] keys37 = {"you live","your home"};
				
				ResponseTemplate response37 = new ResponseTemplate(sentence37, bucket37, keys37);
				
				String[] sentence38 = {"I'm 6'2 and 210 lbs...let's stick to crime fighting relevant questions!"};
				String[][] bucket38 = null;
				String[] keys38 = {"you weigh","tall are you","your height"};
				
				ResponseTemplate response38 = new ResponseTemplate(sentence38, bucket38, keys38);
				
				String[] sentence39 = {".....I have no idea where Gotham is, it's never really come up...New York I guess."};
				String[][] bucket39 = null;
				String[] keys39 = {"where is gotham","where is that"};
				
				ResponseTemplate response39 = new ResponseTemplate(sentence39, bucket39, keys39);
				
				String[] sentence40 = {"What bat cave?....how about that"};
				String[][] bucket40 = null;
				String[] keys40 = {"bat cave"};
				
				ResponseTemplate response40 = new ResponseTemplate(sentence40, bucket40, keys40);
				
				String[] sentence41 = {"Gotham is the city I defend from evil."};
				String[][] bucket41 = null;
				String[] keys41 = {"about gotham","what is gotham"};
				
				ResponseTemplate response41 = new ResponseTemplate(sentence41, bucket41, keys41);
				
				String[] sentence42 = {"I don't know everything. Could we please talk about"};
				String[][] bucket42 = {{" Superman"," The Justice League"," The League of Shadows"}};
				String[] keys42 = {"you don't know"};
				
				ResponseTemplate response42 = new ResponseTemplate(sentence42, bucket42, keys42);
				
				String[] sentence43 = {"Just because. Why do you ask me about"};
				String[][] bucket43 = {{" Superman"," The Justice League"," The League of Shadows"}};
				String[] keys43 = {"why?","so?"};
				
				ResponseTemplate response43 = new ResponseTemplate(sentence43, bucket43, keys43);
				
				String[] sentence44 = {"Let's talk about","or"};
				String[][] bucket44 = {{" Superman"," The Justice League"," The League of Shadows"},{" The Green Lantern"," the Joker", "Gotham"}};
				String[] keys44 = {"talk about?"};
				
				ResponseTemplate response44 = new ResponseTemplate(sentence44, bucket44, keys44);
				
				
				String[] sentence45 = {"Ask away; I'll use my vast internal network to help you."};
				String[][] bucket45 = null;
				String[] keys45 = {"question"};
				ResponseTemplate response45 = new ResponseTemplate(sentence45, bucket45, keys45);
				response45.answerTrigger = true;
				
				
				
	}
	
	public static void main(String[] args)
	{	
		//For WordNet
		//System.setProperty("wordnet.database.dir", "C:\Program Files (x86)\WordNet\2.1\dict");
		BatterBotDriver bat = new BatterBotDriver(new IO());
		
		bat.setup();
		try
		{
			bat.cycle();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println(bat.LP.masterKeys);
		}
		
		System.out.println(bat.LP.memTable.get("type"));
		
	}
	
}
