import java.io.OptionalDataException;
import java.util.Random;

import static java.lang.Math.round;

public class Main {
    public static String[] heroesAttackType = {"Phisical", "Magical", "Kinetic", "Medic"};
    public static int[] heroesHealth = {270, 280, 260, 300};
    public static int[] heroesDamage = {20, 15, 25, 0};
    public static int medicHelp = 25;
    public static int medicIndex = 3;
    public static int bossHealth = 700;
    public static int bossDamage = 50;
    public static String bossDefenceType;
    public static int roundNumber = 0;

    public static void medic() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] < 100 && heroesHealth[i] > 0 && heroesHealth[i] != heroesHealth[medicIndex] && heroesHealth[medicIndex] > 0) {
                Random random = new Random();
                int randomMedic = random.nextInt(heroesHealth.length);
                if (heroesHealth[randomMedic] != heroesHealth[medicIndex]) {
                    heroesHealth[randomMedic] = heroesHealth[randomMedic] + medicHelp;
                    System.out.println("Help " + medicHelp + " " + heroesAttackType[i]);
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        printStatistics();
        while (!isGameFinish()) {
            round();
            medic();
        }
    }

    private static void round() {
        roundNumber++;
        chooseBossDefenceType();
        bossHits();
        heroesHit();
        printStatistics();
    }

    public static void chooseBossDefenceType() {
        Random random = new Random();
        int randNum = random.nextInt(heroesAttackType.length - 1);
        bossDefenceType = heroesAttackType[randNum];
        System.out.println("Boss choose: " + bossDefenceType);
    }

    public static void bossHits() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] < bossDamage) {
                heroesHealth[i] = 0;
            } else {
                heroesHealth[i] = heroesHealth[i] - bossDamage;
            }
        }
    }

    public static void heroesHit() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                if ( heroesAttackType[i] == bossDefenceType) {
                    Random r = new Random();
                    int coef = r.nextInt(8) + 2;

                    if (bossHealth < heroesDamage[i] * coef) {
                        bossHealth =0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i] * coef;
                    }
                    System.out.println("Critical Damage: " + heroesDamage[i] * coef);
                } else {
                    if (bossHealth < heroesDamage[i]) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i];
                    }
                }
            }
        }
    }
    public static Boolean isGameFinish() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!");
            return true;
        }
        boolean allHeroesDead = true;
        for (int j : heroesHealth) {
            if (j > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!");
        }
        return allHeroesDead;
    }

    public static void printStatistics() {
        System.out.println("-----" + roundNumber + " ROUND-----");
        System.out.println("Boss health: " + bossHealth + " [" + bossDamage + "]");
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " + heroesHealth[i] + " [" + heroesDamage[i] + "]");
        }
    }
}




