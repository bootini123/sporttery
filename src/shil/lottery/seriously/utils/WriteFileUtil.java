package shil.lottery.seriously.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map.Entry;

import shil.lottery.sport.entity.VSTeam;

public class WriteFileUtil {
	
	public static void writeEvaluatorRecorder2File(EvaluatorRecorder evaluatorRecorder){
		try {
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(evaluatorRecorder.getName())));
			for(Entry<VSTeam, ContentResult> entry : evaluatorRecorder.getRecorder().entrySet()){
				bufferedWriter.newLine();
				bufferedWriter.write(entry.getKey().toString());
				bufferedWriter.newLine();
				bufferedWriter.write(entry.getValue().toString());
			}
			bufferedWriter.flush();
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
