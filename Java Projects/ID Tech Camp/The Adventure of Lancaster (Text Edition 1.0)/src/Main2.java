import java.util.Scanner;
import java.util.Random;
public class Main2 {

	public static void print(String x) {
		System.out.println(x);
	}
	
	public static void main(String[] args) throws InterruptedException {
			print("PRESS ENTER TO START!                                                                              |");
			Scanner enterText = new Scanner(System.in);
			Scanner numInput = new Scanner(System.in);
			String num = numInput.nextLine();	
			String enterKey = "hi";
			Scanner nameInput = new Scanner(System.in);
			Scanner ynInput = new Scanner(System.in);
			Random randomGenerator = new Random();
			//Player
			double health = 100.0;
			double attack = 30.5;
			double exp = 1;
			double subExp = 0;
			//Enemy
			double batHealth = 40.0;
			double snakeHealth = 60.0;
			double bossHealth = 90.0;
		    print("THE                                                                                                |");
			print("       ###    #######   #     # ########## ##        # ############# #      # ######### ########## |");
			print("      #   #   #      #  #     # #          # #       #       #       #      # #       # #          |");
			print("     #     #  #       # #     # #          #  #      #       #       #      # #       # #          |");
			print("     #     #  #       # #     # #          #   #     #       #       #      # ######### #          |");
			print("     #######  #       #  #   #  #########  #    #    #       #       #      # #      #  #########  |");
			print("     #     #  #       #  #   #  #          #     #   #       #       #      # #       # #          |");
			print("     #     #  #       #  #   #  #          #      #  #       #       #      # #       # #          |");
			print("     #     #  #      #   #   #  #          #       # #       #       #      # #       # #          |");
			print("     #     #  #######     ###   ########## #        ##       #       ######## #       # ########## |");
			print("OF                                                                                                 |");
			print("     #      ###  ##   # #####  ###  ##### ##### ##### #####   ####                                 |");
			print("     #     #   # # #  # #     #   # #       #   #     #   #       #                                |");
			print("     #     ##### #  # # #     ##### #####   #   ####  #####       #     MADE BY: STEFAN UROSEVIC   |");
			print("     #     #   # #  # # #     #   #     #   #   #     #  #       #                                 |");
			print("     ##### #   # #   ## ##### #   # #####   #   ##### #   #   ######                               |");
			print(" THE QUEST OF THE DUNGEON CONTINUES!_______________________________________________________________|");
			Thread.sleep(5000);
			print(" ");
			print("||PRESS ENTER TO CONTINUE||");
			enterKey = enterText.nextLine();
			print(" ");
			print("Wlcome Player Plesase Eneter your name");
			String name = nameInput.nextLine();
			print("Last time your hero in Lancaster your hero entered a dungeon and it must be continued for the evil needs to be rid.");
			System.out.println(name + " it is your duty to finish this off you are going into a dangerous dungeon please be safe!");
			print(" ");
			enterKey = enterText.nextLine();
			print("|=============| " + name + " this dungeon is tedious and you must becareful because there are many test !");
			print("|=THE=DUNGEON=| You slowly find the dungeon! ");
			print("|#############| When you get to the decider YOU MUST MAKE SURE HE SAYS YOU ARE GOOD SO YOU CAN GET THE MAGIC WISH!");
			print("|#############| You see a bat the bat is harmful you are going to kill it for it is the first enemy!");
			print("|#############| But before you can start you retrive a sword from the oss you killed. You find it is destroyed!");
			print("|#############| So you take the bosses big sword which adds + 1.0 to foes you attack! your health is " + health );
			print("|#############| and your attack is " + attack + " +  1 from the sword your ready now to go off to save the world from the evil");
			print("|#############|");
			enterKey = enterText.nextLine();
			print("Are you ready " + name + " y/n");
			String yn = ynInput.nextLine();
				if (yn.equals("y")) {
					print("You go down the dark damp cold cave. You find a torch and light it up look around you see the bat you are ready to fight.");
					print("(Tip: when fighting if you type stat you can bring up you stat by typing stat menu when fighting so you can know what your stats are! But you lose a turn.)");
					enterKey = enterText.nextLine();
				}
				else if (yn.equals("n")) {
					print("How could you let us down " + name);
					System.exit(0);
				}
				do {
				print("Objective: kill the bat! 1 = try hiting it with a sword 2 = light it o fire");
				num = numInput.nextLine();
					 
					if (num.equalsIgnoreCase("stat")) {
						print("|-----------------------------|");
						print("|           STATS             |");
						print("| Health "+health + " Attack " + attack+ "    |");             
						print("| Exp " + exp + " SubExp " + subExp +"          |");
						print("|-----------------------------|");
					}
					if (num.equals("1")){
						int morh = randomGenerator.nextInt(2);
						if (morh == 1) {
						attack = attack + 1;
						print(name + " hit the bat with " + attack + " damge");
						batHealth = batHealth - attack;
						print("The bats health is " + batHealth);
						}
						else if (morh == 0) {
							print("You missed!");						
						}
					}
					else if (num.equals("2")){
						print(name + " burnet the bat with 10 damage!");
						batHealth = batHealth - 10;
						print("The bats health is " + batHealth);
					}

					else { 
						System.exit(0);
					}
						
					
					if (batHealth <= 0.0) {
							print("You have defeated the bat");
							print("You have gained ");
							subExp = randomGenerator.nextInt(4)+6;
							print("You have gained " + subExp + " exp!");
							break;
					}
					
					int batDamage = randomGenerator.nextInt(9)+3;
					print("The bat hits you with " + batDamage);
					health = health - batDamage;
					print(name + "'s health is now " + health );
					
					
					
					if (health <= 0.0) {
						print("YOU ARE DEAD");
						break;
					}
					
				} while (health > 0 && batHealth > 0); 
				
				
				enterKey = enterText.nextLine();
				
				print("You gown down deeper in the cave. It gets colder and tricky you find a chest!");
				print("(Tip: You have a 50/50 of opening a chest with a gohst in it chest being a gohst)!");
				enterKey = enterText.nextLine();
				print("Do you want to open the chest y/n");
				yn = ynInput.nextLine();
				if (yn.equals("y")){
					int morh = randomGenerator.nextInt(2);
						if (morh == 1) {
							print("You got a health and strength upgrade!");
							health = health + 20.0;
							attack = attack + 5.0;
							print("Your health is " + health + " and your attack is " + attack);
						}
						if (morh == 0){
							int gohst = randomGenerator.nextInt(11)+6;
							health = health - gohst;
							print("A gohst has popped out at your health is at " + health);
					}
								
				}
				else if (yn.equals("n")) {
					print("You continued down the dark cave.");
				}
				else {
					System.exit(0);
				}
				//----------------------------------------------------------------------------
				
				
				enterKey = enterText.nextLine();
				
				print("As you walk down the cave you see pictures and ancient writing!   /-|");
				print("You see a picture of the MAGIC WISH                               |o|");
				print("you know you need to get to the MAGIC WISH to save the world!     |o|");
				print("You realize it's not a wish it's a MAGIC WISH FLUTE!              |_|");
				enterKey = enterText.nextLine();
				print("You do see a powerfull sword that can destroy and you take the image!");
				enterKey = enterText.nextLine();
				print(" /|\\   ");
				print(" | |");
				print(" | |");
				print(" | |");
				print(" | |");
				print(" |_|");
				print(" ===");
				print("  | ");
				print("  | ");
				print("  O ");
				
				enterKey = enterText.nextLine();
				
				print("You go farther unitl the cave collapses!");
				print("What do you want to do? 1 = go down a second tunnel or 2 = Try to climb back up");
				num = numInput.nextLine();
				if (num.equals("1")) { 
					print("You go down and see a poisonous snake you must battle it to get past!");
					print("(Tip: When battleing a snake or a fast creature you always wanna do a quick attack!)");
					}
				else if (num.equals("2")) {
					print("You climb back up then all of a sudden you slipped on the edge and fall on your head!");
					print("YOU ARE DEAD");
					System.exit(0);
				}
				else {
					System.exit(0);
				}
				
				
				do{
				print("Objective: Kill the snake 1 = Hitting with sword 2 = Quick attack 3 = Burn it with a torch.");
				num = numInput.nextLine();
				if (num.equalsIgnoreCase("stat")) {
					print("|-----------------------------|");
					print("|           STATS             |");
					print("| Health "+health + " Attack " + attack+ "    |");             
					print("| Exp " + exp + " SubExp " + subExp +"          |");
					print("|-----------------------------|");
				}
				else if (num.equals("2")) {
						print(name + " hit the snake with 15 damage!");
						snakeHealth = snakeHealth - 15;
						print("The snakes health is " + snakeHealth);	
				}
				else if(num.equals("1")) {
						int morh = randomGenerator.nextInt(2);
						if (morh == 0) {
							print(" You missed!");
						}
						else if (morh == 1){
							print(name + " hit the snake ");
							snakeHealth = snakeHealth - attack;
							print("The snakes health is now " + snakeHealth);
						}
				}
				else if (num.equals("3")){
						int morh = randomGenerator.nextInt(2);
						if (morh == 1 ){
							print("You hit the snake with 15 damage!");
							snakeHealth = snakeHealth - 15;
							print("The snakes health is " + snakeHealth);
						}	subExp = randomGenerator.nextInt(4)+6;
							
						if (morh == 0) {
							print("You missed!");
						}
				}
				else {
					System.exit(0);
				}
				
				
				if (snakeHealth < 0.0){
					health = health + 10.0;
					attack = attack + 5.0;
					print("You defeated the snake your health is now " + health + " your attack is now " + attack);
					subExp = randomGenerator.nextInt(4)+6;
					print("You get exp " + subExp);
					if (subExp <= 10) {
					exp = exp + 1;
					print("LEVEL UP you are level " + exp);
					}
					break;
				}
				
					int snakeDamage = randomGenerator.nextInt(16)+6;
					print("The snake hits you with " + snakeDamage );
					health = health - snakeDamage ;
					print("Your health is now " + health);
				
				if (health < 0.0){
					print("YOU ARE DEAD");
				}

				
				} while (health > 0 && snakeHealth > 0);
				enterKey = enterText.nextLine();
				print("You look around at the old stone unsure where you are so you continue straight forward.");
				enterKey = enterText.nextLine();
				print("You walk forward you accidentally find a trap door and you meet a BOSS! (from the evil side)");
				enterKey = enterText.nextLine();
				print("BOSS: I have found the TELLER and the MAGIC WISH FLUTE will be mine! Mwahahaha!");
				enterKey = enterText.nextLine();
				print(name + ": I will never let you go to him i will defeat the evil and you cant stop me!");
				print("BOSS: LOOK AT ME IM POWERFUL!");
				enterKey = enterText.nextLine();
				print(name + ": Well your just a block head!");
				enterKey = enterText.nextLine();
				print("BOSS :You fool lets fight!");
				enterKey = enterText.nextLine();
				print(name + ": hears the elder in his mind. Elder: Don't let him win at all!");
				enterKey = enterText.nextLine();
				print("You see the battle area. Two torches on the wall and old stone. It smells bad but your close!");
				enterKey = enterText.nextLine();
				
				do {
					print("Objective: Defeat the BOSS! 1 = hit it with a sword 2 = quick attack 3 = power attack");
					num = numInput.nextLine();
						 
						if (num.equalsIgnoreCase("stat")) {
							print("|-----------------------------|");
							print("|           STATS             |");
							print("| Health "+health + " Attack " + attack+ "    |");             
							print("| Exp " + exp + " SubExp " + subExp +"          |");
							print("|-----------------------------|");
						}
						if (num.equals("1")){
							attack = attack + 1;
							print(name + " hit the Boss with " + attack + " damge");
							bossHealth = bossHealth - attack;
							print("The BOSS health is " + bossHealth);						
							}
						
						else if (num.equals("2")){
							print(name + " quick attacked BOSS with 10 damage!");
							bossHealth = bossHealth - 10;
							print("The BOSS health is " + bossHealth);
						}
						else if (num.equals("3")){
							attack = attack + 10;
							print(name + " hit the BOSS with " + attack);
							bossHealth = bossHealth - attack;
						}

						else { 
							System.exit(0);
						}
							
						
						if (bossHealth <= 0.0) {
								print("You have defeated the BOSS");
								print("You have gained ");
								subExp = randomGenerator.nextInt(4)+6;
								print("You have gained " + subExp + " exp!");
								break;
						}
						
						int bossDamage = randomGenerator.nextInt(21)+3;
						print("The BOSS hits you with " + bossDamage);
						health = health - bossDamage;
						print(name + "'s health is now " + health );
						
						
						
						if (health <= 0.0) {
							print("YOU ARE DEAD");
							break;
						}
						
					} while (health > 0 && bossHealth > 0);
				
				if (subExp <= 10) {
					exp = exp + 1;
					print("LEVEL UP you are level " + exp);
					}
				enterKey = enterText.nextLine();
				print("You go further down the cave and it gets super dark.");
				enterKey = enterText.nextLine();
				print("You see light and you go closer.");
				enterKey = enterText.nextLine();
				print("You here a voice that says WELCOME TO THE TELLER.");
				enterKey = enterText.nextLine();
				print("You have arrived at THE TELLER!");
				enterKey = enterText.nextLine();
				print("Now " + name + " Choose the grren side or red side! g = Green r = Red");
				num = numInput.nextLine();
				
				if (num.equals("r")){
				print("You have choosen wrong and you have let everyone down!");
				System.exit(0);
				}
				
				else if (num.equals("g")) {
					print("YOU HAVE CHOOSEN RIGHT NOW YOU MAY CONTINUE TO THE MAGIC FLUTE " + name);
					enterKey = enterText.nextLine();
				}
				
				else {
					System.exit(0);
				}
				
				print("You see the door open to continue!");
				enterKey = enterText.nextLine();
				print("Thanks for playing +---------+");
				print("Continue on part 3 |         |");
				print("Created by         |         |");
				print("Stefan Urosevic    |         |");
				print("  The End          |         |");
				
	}
}
