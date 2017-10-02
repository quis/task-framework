package uk.nhs.ers.task.common.mock;


import java.sql.Timestamp;

import uk.nhs.ers.task.common.task.AbstractTaskParameters;


public class MockTaskParameters extends AbstractTaskParameters
{
	private String param1;
	private Long param2;
	private Timestamp param3;


	public MockTaskParameters()
	{}


	public MockTaskParameters(final String param1, final Long param2, final Timestamp param3)
	{
		this.param1 = param1;
		this.param2 = param2;
		this.param3 = param3;
	}


	/**
	 * Returns the value for the field {@link param1}
	 */
	public String getParam1()
	{
		return this.param1;
	}


	/**
	 * Sets the value for the field {@link param1}
	 */
	public void setParam1(final String param1)
	{
		this.param1 = param1;
	}


	/**
	 * Returns the value for the field {@link param2}
	 */
	public Long getParam2()
	{
		return this.param2;
	}


	/**
	 * Sets the value for the field {@link param2}
	 */
	public void setParam2(final Long param2)
	{
		this.param2 = param2;
	}


	/**
	 * Returns the value for the field {@link param3}
	 */
	public Timestamp getParam3()
	{
		return this.param3;
	}


	/**
	 * Sets the value for the field {@link param3}
	 */
	public void setParam3(final Timestamp param3)
	{
		this.param3 = param3;
	}


	@Override
	public boolean equals(final Object o)
	{
		boolean ret = false;
		if (o instanceof MockTaskParameters)
		{
			final MockTaskParameters omtp = (MockTaskParameters)o;
			ret = paramEquals(getParam1(), omtp.getParam1())
					&& paramEquals(getParam2(), omtp.getParam2())
					&& paramEquals(getParam3(), omtp.getParam3());

		}
		return ret;
	}


	private boolean paramEquals(final Object param, final Object other)
	{
		boolean ret = false;
		if (param == null)
		{
			ret = (other == null);
		}
		else
		{
			ret = param.equals(other);
		}
		return ret;
	}
}
