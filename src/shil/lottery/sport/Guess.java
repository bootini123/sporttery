package shil.lottery.sport;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

import shil.lottery.sport.analyze.AnalyzeDiffLeagueCorrectPercent;
import shil.lottery.sport.analyze.AnalyzeTeamMatchResult;
import shil.lottery.sport.analyze.TeamMatchResult;
import shil.lottery.sport.db.SportMetaDaoImpl;
import shil.lottery.sport.entity.VSTeam;
import shil.lottery.sport.excel.LoadExcelData2VSTeams;
import shil.lottery.sport.guess.Evaluator;
import shil.lottery.sport.guess.Guess4TeamMatchResult2;
import shil.lottery.sport.guess.Guess4TeamMatchResult3;
import shil.lottery.sport.guess.Guess4TeamScores1;
import shil.lottery.sport.guess.GuessCardsCircleMatchResult;
import shil.lottery.sport.guess.GuessFour;
import shil.lottery.sport.guess.GuessThree;
import shil.lottery.sport.guess.GuessTwo;
import shil.lottery.sport.guess.GuessUtils;
import shil.lottery.sport.score.GuessScoreLeagueProbability;
import shil.lottery.sport.score.GuessScoreVSTeamProbability;
import shil.lottery.sport.score.GuessScoreVSTeamWeight;


public class Guess {

	public static void main(String[] args) {
		
		List<VSTeam> vsTeams = SportMetaDaoImpl.loadEveryVSTeamRecords();
		
		List<VSTeam> guessTeams = LoadExcelData2VSTeams.justDoIt(new File("d:\\abc.xls"));
		
		Guess4TeamMatchResult2 result = new GuessTwo();
		Guess4TeamMatchResult3 result3 = new GuessThree();
		Guess4TeamMatchResult3 result4 = new GuessFour();
		Guess4TeamMatchResult3 result5 = new GuessCardsCircleMatchResult();
		Guess4TeamScores1 score = new GuessScoreLeagueProbability();
		Guess4TeamScores1 vsscore = new GuessScoreVSTeamProbability();
		Guess4TeamScores1 weightscore = new GuessScoreVSTeamWeight();
		
		Map<String, TeamMatchResult> tmrs = AnalyzeTeamMatchResult.analyzeTeamMatchResult(vsTeams);
		
		for(VSTeam vs : guessTeams)
		{
			System.out.println("\n* * *");
			System.out.println(vs.getLeague());
			System.out.println(vs.getVs()[0] +" vs " +vs.getVs()[1]);
			
			int rr = result.guess4teamMatchResult(vs).getMatch_Result();
			if(rr>=0)
			{
				System.out.println(result.guess4teamMatchResult(vs));
				System.out.println("~2~~result is :  " + rr);
				Evaluator.evaluatorGuessTwo();
				System.out.println("~3~~result is :  " + result3.guess4teamMatchResult(vsTeams, vs));
				Evaluator.evaluatorGuessThree();
				System.out.println("~4~~result is :  " + result4.guess4teamMatchResult(vsTeams, vs));
				Evaluator.evaluatorGuessFour();
			}
			System.out.println("~5~~result is :  " + result5.guess4teamMatchResult(vsTeams, vs));
//			Evaluator.evaluatorGuessCardsCircleMatchResult();
			System.out.println("host success: "+AnalyzeDiffLeagueCorrectPercent.getAllRecordsLeagueHostGuessRateMap().get(vs.getLeague()));
			System.out.println("people success: "+AnalyzeDiffLeagueCorrectPercent.getAllRecordsLeaguePeopleGuessRateMap().get(vs.getLeague()));
			System.out.println("a: "+tmrs.get(vs.getVs()[0]));
			System.out.println("b: "+tmrs.get(vs.getVs()[1]));
			System.out.println();
			Set<Integer> scores = score.guess4teamScores(vsTeams, vs, false);
			if(GuessUtils.isGuessScoreLegal(scores))
			{
				score.guess4teamScores(vsTeams, vs, true);
				System.out.println("~~~league avg score is :  " +scores);
			}
			
			Set<Integer> vscores = vsscore.guess4teamScores(vsTeams, vs, false);
			if(GuessUtils.isGuessScoreLegal(vscores))
			{
				System.out.println(" vs team scores: :)");
				System.out.println("finally: "+ vsscore.guess4teamScores(vsTeams, vs, true));
			}
			
			Set<Integer> wscores = weightscore.guess4teamScores(vsTeams, vs, false);
			if(GuessUtils.isGuessScoreLegal(wscores))
			{
				System.out.println(" weight team scores: :)");
				System.out.println("finally weight: "+ weightscore.guess4teamScores(vsTeams, vs, true));
			}
			
		}
	}

}
