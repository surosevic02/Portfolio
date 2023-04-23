import java.util.Scanner;
import java.util.Random;
public class Main {

	public static void print(String x) {
		System.out.println(x);
	}
	
	public static void main(String[] args) throws InterruptedException {
		print("Welcome to the Adventures of Lancaster please enter your name.");
		Scanner nameInput = new Scanner (System.in);
		String name = nameInput.nextLine();
		print("One day the Lancaster Key was missing many went to look for it and thoose many where evil.");
		Thread.sleep(3000);
		System.out.println("Only you can save the world from the evil foes " + name +" they need to be stoped otherwise no peace will exist!");
		Thread.sleep(3000);
		print("Are you ready? y/n");
		Scanner startInput = new Scanner (System.in);
		String start = startInput.nextLine();
		if(start.equals ("y"))  {
			System.out.println(name +" wake up you need to get the sword and fight");
		}
		else {
			System.out.println("Goodbye");
			System.exit(0);
		}
		double health = 3.0;
		double strength = 1.0;
		System.out.println("Your health is " + health + " and your strength is " + strength);
		Scanner yn1Input = new Scanner (System.in); 		
		print("You grab your sword and go outside your meet a guard but he is evil do you want to run or fight? y = fight/n = run");
		String yn1 = yn1Input.nextLine();	
		if (yn1.equals("y")) {
			System.out.println(name + " hit the guard the guard was knocked out!");
			health = health + 1.0;
			strength = strength + 2.5;
			System.out.println("You ranked up your health " + health + " and your strength " + strength);
			
			
		}
		else {
			System.out.println(name +" ran away but fell down a deep hole. YOU ARE DEAD");
			System.exit(0);
			}
		
		System.out.println(name + ": That guard was too weak. I'm wondering if their army is weak?");
		Thread.sleep(3000);
		System.out.println(name + " goes down the hill (cause his/her house was on a hill) he sees guards and hides in a bush he let them pass cause there was too much.");
		Thread.sleep(3000);
		print("There are 3 caves which one do you want to go down 1, 2, or 3");
		String ott = nameInput.nextLine();
		if (ott.equals ("1")) {
			System.out.println(name + " walked down cave one then suddenly the cave collapsed health lost -4.0");
			health = health - 4.0;
			
			if (health <= 0.0 ){
				System.out.println(name +" had died! GAME OVER");
				System.exit(0);
			}
			else {
				
			}
				
		}
		else if (ott.equals ("2")){
			System.out.println(name + " has found a secret room it has a chest would you like to open it y/n");
			 yn1 = nameInput.nextLine();
			if (yn1.equals("y")) {
				System.out.println(name + " has found a health and strength upgrade");
				Thread.sleep(3000);
				health = health + 1.0;
				strength = strength + 1.0;
				System.out.println("Your health is now " + health + " and your strength is " + strength +"!");
				Thread.sleep(3000);
				System.out.println("After going in the cave and opening a chest he found a secret passage and went to Loco Village");
			}
			else if (yn1.equals("n")){
				System.out.println(name + " did not open the chest but went to the other side to Loco Village");
			}
			else {
				System.exit(0);
			}
		}
		else if (ott.equals("3")){
			System.out.println(name + " Found a secret passage through and ends up in Loco Village");
		}
		else {
			System.exit(0);
		}
		
		double guardHealth = 8;
		System.out.println(name + " has approched a guaurd heavaly armoured but " + name + " had no choice but to fight! For the guard did'nt let any one in the village!");
		Random randomGenerator = new Random();
		Thread.sleep(3000);
		System.out.println(name + " Hits with " + strength + " damage!");
		guardHealth = guardHealth - strength;
		System.out.println("The guards health is now " + guardHealth );
		Thread.sleep(3000);
		int guard = randomGenerator.nextInt(1)+2;
		System.out.println("Guard hit hits with " + guard + " damage!");
		health = health - guard;
		System.out.println(name +"'s health is at " + health );
		Thread.sleep(3000);
		System.out.println(name + " has two options 1 = hit with sword 2 = Or kick the guard in his weak spot");
		ott = nameInput.nextLine();
		if (ott.equals("1")) {
			System.out.println(name + " hits the guard with " + strength + " damage!");
			guardHealth = guardHealth - strength;
			System.out.println("The guards health is now " + guardHealth);
			if (health <= 0.0 ){
				System.out.println(name +" had died! GAME OVER");
				System.exit(0);
			}
			else {
				
			}
			if (guardHealth <= 0.0){
				System.out.println("You have defeated the guard!");
				health = health + 1;
				strength = strength + 0.5;
				System.out.println("Your health is " + health + " and your strength is " + strength);
			}
		}
		else if (ott.equals("2")) {
			System.out.println(name + " kicked the guard with 6 damage!");
			guardHealth = guardHealth - 6;
				if (guardHealth <= 0.0){
					System.out.println("You have defeated the guard!");
					strength = strength + 1.5;
					health = health + 2;
					System.out.println(name +" your health is at " + health + " and your strength is " + strength);
				}
				else{
					
				}				
		}
		else {
			System.exit(0);			
		}
		double bossHealth = 15.0;
		print("You go to the village elder he tells you where the key is because he is the desendant of the hider.");
		Thread.sleep(3000);
		print("After he tells you where the key is he vanishes. The next week you hid while going to the hidng place of the key.");
		health = health + 4;
		strength = strength + 3;
		System.out.println("While going to the hiding place you pick up 4 health your health is now " + health + " and u picked up 3 strength your strength is " + strength);
		Thread.sleep(3000);
		print("When you finally found the key the a boss appeared from the evil side and you had to face him!");
		
		 do {
		print("1 = sword attack 2 = kick 3 = Quick attack");
		ott = nameInput.nextLine();
		if (ott.equals("1")){
			System.out.println(name + " has hit the Boss with a sword with " + strength + " damge!");
			bossHealth = bossHealth - strength;
			System.out.println("The Bosses health is now " + bossHealth);
			Thread.sleep(3000);
			int boss = randomGenerator.nextInt(2)+4;
			if (bossHealth <= 0.0){
				 System.out.println("You have defeated the boss! " + name + " HAS OBTAINED THE SECRET KEY");
				 Thread.sleep(3000);
				 print("You go into your dungeon and now you have a new quest!");
				 Thread.sleep(3000);
				 print("Thanks for playing! Please play The Adventures of Lancaster 2 the quest continues!");
				 Thread.sleep(3000);
				 print("Made by Stefan Urosevic + help.");
				 System.exit(0);	
			}
			else if (health <= 0.0){
				print("YOU ARE DEAD");
			}
			health = health - boss;
			System.out.println("The Boss has hit you, you now have " + health + " of health");			
		}
		else if (ott.equals("2")) {
			System.out.println(name + " hits the Boss with 3 damage!");
			bossHealth = bossHealth - 3;
			System.out.println("The Bosses health is now " + bossHealth + "");
			Thread.sleep(3000);
			int boss = randomGenerator.nextInt(2)+4;
			if (bossHealth <= 0.0){
				 System.out.println("You have defeated the boss! " + name + " HAS OBTAINED THE SECRET KEY");
				 Thread.sleep(3000);
				 print("You go into your dungeon and now you have a new quest!");
				 Thread.sleep(3000);
				 print("Thanks for playing! Please play The Adventures of Lancaster 2 the quest continues!");
				 Thread.sleep(3000);
				 print("Made by Stefan Urosevic + help.");
				 System.exit(0);	
			}
			else if (health <= 0.0){
				print("YOU ARE DEAD");
			}
			health = health - boss;
			System.out.println("The Boss has hit you, you now have " + health + " of health");	
		}
		else if (ott.equals("3")) { 
			System.out.println(name + " Hit the Boss with 2 damage!");
			bossHealth = bossHealth - 2;
			System.out.println("The Bosses health is now " + bossHealth);
			Thread.sleep(3000);
			int boss = randomGenerator.nextInt(2)+4;
			if (bossHealth <= 0.0){
				 System.out.println("You have defeated the boss! " + name + " HAS OBTAINED THE SECRET KEY");
				 Thread.sleep(3000);
				 print("You go into your dungeon and now you have a new quest!");
				 Thread.sleep(3000);
				 print("Thanks for playing! Please play The Adventures of Lancaster 2 the quest continues!");
				 Thread.sleep(3000);
				 print("Made by Stefan Urosevic + help.");
				 System.exit(0);	
			}
			health = health - boss;
			System.out.println("The Boss has hit you, you now have " + health + " of health");	
			
			 if (health <= 0.0){
				print("YOU ARE DEAD");
			}
			
		}
		else {
			System.exit(0);
		}
		 } 
		 while (bossHealth > 0 && health > 0);
		

	}
	
}