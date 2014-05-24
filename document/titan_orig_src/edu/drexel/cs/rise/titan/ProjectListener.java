package edu.drexel.cs.rise.titan;

public abstract interface ProjectListener
{
  public abstract void dependencyChanged(Project paramProject);

  public abstract void clusterChanged(Project paramProject);

  public abstract void modified(Project paramProject);
}

/* Location:           C:\Users\정근\Desktop\titan.jar
 * Qualified Name:     edu.drexel.cs.rise.titan.ProjectListener
 * JD-Core Version:    0.6.2
 */