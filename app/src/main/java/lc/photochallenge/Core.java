package lc.photochallenge;

import android.util.SparseArray;

import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lc.photochallenge.models.Category;
import lc.photochallenge.models.Challenge;
import lc.photochallenge.models.Follow;
import lc.photochallenge.models.Submission;

public class Core {
    public static ArrayList<Category> categories;
    public static Category selectedCategory;
    public static Challenge selectedChallenge;

    public static ArrayList<ParseObject> submissions;
    public static List<Follow> friends;
    public static SparseArray<ArrayList<Challenge>> Challenges = new SparseArray<ArrayList<Challenge>>();
    public static HashMap<Challenge , Submission> Submissions = new HashMap<Challenge , Submission>();
    public static int totalChallenges;
    public static int completedChallenges;
}
