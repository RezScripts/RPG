package me.rezscipts.rpg.players;

import me.rezscipts.rpgexperience.utils.RMessages;

public class PlayerStatics {

    public static long getEXPForNextLevel(int level) {
        if (level < 1)
            level = 1;
        long ret = 1;
        if (level <= 200) {
            ret = PlayerStatics.EXP_REQUIREMENTS[level - 1][1];
        } else {
            ret = (int) (Math.pow(1.0548, level - 200) * EXP_REQUIREMENTS[EXP_REQUIREMENTS.length - 1][1]);
        }
        if (ret < 0) {
            RMessages.announce("Error code 105: EXP. Please report this to RezScipts.");
            ret = Long.MAX_VALUE;
        }
        return ret;
    }

    private static final int[][] EXP_REQUIREMENTS = {
            { 1, 15 },
            { 2, 34 },
            { 3, 57 },
            { 4, 92 },
            { 5, 135 },
            { 6, 372 },
            { 7, 560 },
            { 8, 840 },
            { 9, 1242 },
            { 10, 1144 },
            { 11, 1573 },
            { 12, 2144 },
            { 13, 2800 },
            { 14, 3640 },
            { 15, 4700 },
            { 16, 5893 },
            { 17, 7360 },
            { 18, 9144 },
            { 19, 11120 },
            { 20, 13477 },
            { 21, 16268 },
            { 22, 19320 },
            { 23, 22880 },
            { 24, 27008 },
            { 25, 31477 },
            { 26, 36600 },
            { 27, 42444 },
            { 28, 48720 },
            { 29, 55813 },
            { 30, 63800 },
            { 31, 86784 },
            { 32, 98208 },
            { 33, 110932 },
            { 34, 124432 },
            { 35, 139372 },
            { 36, 155865 },
            { 37, 173280 },
            { 38, 192400 },
            { 39, 213345 },
            { 40, 235372 },
            { 41, 259392 },
            { 42, 285532 },
            { 43, 312928 },
            { 44, 342624 },
            { 45, 374760 },
            { 46, 408336 },
            { 47, 445544 },
            { 48, 483532 },
            { 49, 524160 },
            { 50, 567772 },
            { 51, 598886 },
            { 52, 631704 },
            { 53, 666321 },
            { 54, 702836 },
            { 55, 741351 },
            { 56, 781976 },
            { 57, 824828 },
            { 58, 870028 },
            { 59, 917625 },
            { 60, 967995 },
            { 61, 1021041 },
            { 62, 1076994 },
            { 63, 1136013 },
            { 64, 1198266 },
            { 65, 1263930 },
            { 66, 1333194 },
            { 67, 1406252 },
            { 68, 1483314 },
            { 69, 1564600 },
            { 70, 1650340 },
            { 71, 1740778 },
            { 72, 1836173 },
            { 73, 1936794 },
            { 74, 2042930 },
            { 75, 2154882 },
            { 76, 2272970 },
            { 77, 2397528 },
            { 78, 2528912 },
            { 79, 2667496 },
            { 80, 2813674 },
            { 81, 2967863 },
            { 82, 3130502 },
            { 83, 3302053 },
            { 84, 3483005 },
            { 85, 3673873 },
            { 86, 3875201 },
            { 87, 4087562 },
            { 88, 4311559 },
            { 89, 4547832 },
            { 90, 4797053 },
            { 91, 5059931 },
            { 92, 5337215 },
            { 93, 5629694 },
            { 94, 5938202 },
            { 95, 6263614 },
            { 96, 6606860 },
            { 97, 6968915 },
            { 98, 7350811 },
            { 99, 7753635 },
            { 100, 8178534 },
            { 101, 8626718 },
            { 102, 9099462 },
            { 103, 9598112 },
            { 104, 10124088 },
            { 105, 10678888 },
            { 106, 11264090 },
            { 107, 11881362 },
            { 108, 12532461 },
            { 109, 13219239 },
            { 110, 13943653 },
            { 111, 14707765 },
            { 112, 15513750 },
            { 113, 16363902 },
            { 114, 17260644 },
            { 115, 18206527 },
            { 116, 19204245 },
            { 117, 20256637 },
            { 118, 21366700 },
            { 119, 22537594 },
            { 120, 23772654 },
            { 121, 25075395 },
            { 122, 26449526 },
            { 123, 27898960 },
            { 124, 29427822 },
            { 125, 31040466 },
            { 126, 32741483 },
            { 127, 34535716 },
            { 128, 36428273 },
            { 129, 38424542 },
            { 130, 40530206 },
            { 131, 42751262 },
            { 132, 45094030 },
            { 133, 47565183 },
            { 134, 50171755 },
            { 135, 52921167 },
            { 136, 55821246 },
            { 137, 58880250 },
            { 138, 62106888 },
            { 139, 65510344 },
            { 140, 69100311 },
            { 141, 72887008 },
            { 142, 76881216 },
            { 143, 81094306 },
            { 144, 85594273 },
            { 145, 90225770 },
            { 146, 95170142 },
            { 147, 100385466 },
            { 148, 105886589 },
            { 149, 111689174 },
            { 150, 117809740 },
            { 151, 124265714 },
            { 152, 131075474 },
            { 153, 138258410 },
            { 154, 145834970 },
            { 155, 153826726 },
            { 156, 162256430 },
            { 157, 171148082 },
            { 158, 180526997 },
            { 159, 190419876 },
            { 160, 200854885 },
            { 161, 211861732 },
            { 162, 223471711 },
            { 163, 223471711 },
            { 164, 248635353 },
            { 165, 262260570 },
            { 166, 276632449 },
            { 167, 291791906 },
            { 168, 307782102 },
            { 169, 324648562 },
            { 170, 342439302 },
            { 171, 361204976 },
            { 172, 380999008 },
            { 173, 401877754 },
            { 174, 423900654 },
            { 175, 447130410 },
            { 176, 471633156 },
            { 177, 497478653 },
            { 178, 524740482 },
            { 179, 553496261 },
            { 180, 583827855 },
            { 181, 615821622 },
            { 182, 649568646 },
            { 183, 685165008 },
            { 184, 722712050 },
            { 185, 762316670 },
            { 186, 804091623 },
            { 187, 848155844 },
            { 188, 894634784 },
            { 189, 943660770 },
            { 190, 995373379 },
            { 191, 1049919840 },
            { 192, 1107455447 },
            { 193, 1168144006 },
            { 194, 1232158297 },
            { 195, 1299680571 },
            { 196, 1370903066 },
            { 197, 1446028554 },
            { 198, 1525246918 },
            { 199, 1608855764 },
            { 200, 1697021059 },
    };
}
