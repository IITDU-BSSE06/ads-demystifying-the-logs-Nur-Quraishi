package iit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.regex.Pattern;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class Driver 
{	  
	  public static class Map extends Mapper<Object, Text, Text, LongWritable>
	  {
		    private final static LongWritable one = new LongWritable(1);
		    private Text word = new Text();
		      
		    public void map(Object key, Text value, Context context) throws IOException, InterruptedException 
		    {
		    	BufferedReader br = new BufferedReader(new StringReader(value.toString()));
		    	String line = br.readLine();
		    	
		    	while (line != null) 
		    	{
		    		String date[] = line.trim().split(" ");
		    		if(date.length == 10)
		    		{
		    			String[] roughDate = date[3].trim().split(Pattern.quote("/"));
		    			if(roughDate.length == 3)
		    			{
		    				String[] roughYear = roughDate[2].trim().split(Pattern.quote(":"));
		    				if(roughYear.length == 4)
		    				{
		    					if(Integer.valueOf(roughYear[0].trim()) > 2008 && Integer.valueOf(roughYear[0].trim()) < 2012)
		    						word.set(roughYear[0].trim());
		    				}
		    			}
		    		}
		    		context.write(word, one);
		    		line = br.readLine();
		    	}
		    }
	  }
	  
	  public static class Reduce extends Reducer<Text,LongWritable,Text,LongWritable> 
	  {
		    private LongWritable result = new LongWritable();
	
		    public void reduce(Text key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException 
		    {
		    	long sum = 0;
			    for (LongWritable val : values) 
			    {
			    	sum += val.get();
			    }
			    result.set(sum);
			    context.write(key, result);
		    }
		    
	  }
	
	  @SuppressWarnings("deprecation")
	  public static void main(String[] args) throws Exception 
	  {
		    Configuration conf = new Configuration();
		    String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
		    if (otherArgs.length != 2) 
		    {
		      System.err.println("Usage: wordcount <in> <out>");
		      System.exit(2);
		    }
		    Job job = new Job(conf, "assignment6");
		    job.setJarByClass(Driver.class);
		    job.setMapperClass(Map.class);
		    job.setCombinerClass(Reduce.class);
		    job.setReducerClass(Reduce.class);
		    job.setOutputKeyClass(Text.class);
		    job.setOutputValueClass(LongWritable.class);
		    FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
		    FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
		    System.exit(job.waitForCompletion(true) ? 0 : 1);
	  }
}