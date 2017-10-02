package uk.nhs.ers.task.common.mock;


import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MockResultSet implements ResultSet
{
	private static final Logger LOG = LoggerFactory
			.getLogger(MockResultSet.class);

	private final Map<String, Object> map = new HashMap<String, Object>();


	public void addField(final String columnLabel, final Object value)
	{
		this.map.put(columnLabel, value);
	}


	@Override
	public long getLong(final String columnLabel) throws SQLException
	{
		LOG.info("columnLabel = " + columnLabel);

		final Long result = (Long)this.map.get(columnLabel);

		if (!this.map.containsKey(columnLabel))
		{
			throw new RuntimeException("field not specified for columnLabel = " + columnLabel);
		}

		return result;
	}


	@Override
	public Date getDate(final String columnLabel) throws SQLException
	{
		LOG.info("columnLabel = " + columnLabel);

		final Date result = (Date)this.map.get(columnLabel);

		if (!this.map.containsKey(columnLabel))
		{
			throw new RuntimeException("field not specified for columnLabel = " + columnLabel);
		}

		return result;
	}


	@Override
	public Timestamp getTimestamp(final String columnLabel) throws SQLException
	{
		LOG.info("columnLabel = " + columnLabel);

		final Timestamp result = (Timestamp)this.map.get(columnLabel);

		if (!this.map.containsKey(columnLabel))
		{
			throw new RuntimeException("field not specified for columnLabel = " + columnLabel);
		}

		return result;
	}


	@Override
	public String getString(final String columnLabel) throws SQLException
	{
		LOG.info("columnLabel = " + columnLabel);

		final String result = (String)this.map.get(columnLabel);

		if (!this.map.containsKey(columnLabel))
		{
			throw new RuntimeException("field not specified for columnLabel = " + columnLabel);
		}

		return result;
	}


	@Override
	public int getInt(final String columnLabel) throws SQLException
	{
		LOG.info("columnLabel = " + columnLabel);

		final Integer result = (Integer)this.map.get(columnLabel);

		if (!this.map.containsKey(columnLabel))
		{
			throw new RuntimeException("field not specified for columnLabel = " + columnLabel);
		}

		return result;
	}


	
	@Override
	public <T> T unwrap(final Class<T> iface) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return null;
	}


	@Override
	public boolean isWrapperFor(final Class<?> iface) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return false;
	}


	@Override
	public boolean next() throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return false;
	}


	@Override
	public void close() throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public boolean wasNull() throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return false;
	}


	@Override
	public String getString(final int columnIndex) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return null;
	}


	@Override
	public boolean getBoolean(final int columnIndex) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return false;
	}


	@Override
	public byte getByte(final int columnIndex) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return 0;
	}


	@Override
	public short getShort(final int columnIndex) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return 0;
	}


	@Override
	public int getInt(final int columnIndex) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return 0;
	}


	@Override
	public long getLong(final int columnIndex) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return 0;
	}


	@Override
	public float getFloat(final int columnIndex) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return 0;
	}


	@Override
	public double getDouble(final int columnIndex) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return 0;
	}


	@Override
	public BigDecimal getBigDecimal(final int columnIndex, final int scale) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return null;
	}


	@Override
	public byte[] getBytes(final int columnIndex) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return null;
	}


	@Override
	public Date getDate(final int columnIndex) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return null;
	}


	@Override
	public Time getTime(final int columnIndex) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return null;
	}


	@Override
	public Timestamp getTimestamp(final int columnIndex) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return null;
	}


	@Override
	public InputStream getAsciiStream(final int columnIndex) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return null;
	}


	@Override
	public InputStream getUnicodeStream(final int columnIndex) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return null;
	}


	@Override
	public InputStream getBinaryStream(final int columnIndex) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return null;
	}


	@Override
	public boolean getBoolean(final String columnLabel) throws SQLException
	{
		LOG.info("columnLabel = " + columnLabel);

		final Boolean result = (Boolean)this.map.get(columnLabel);

		if (result == null)
		{
			throw new RuntimeException("field not specified for columnLabel = " + columnLabel);
		}

		return result;
	}


	@Override
	public byte getByte(final String columnLabel) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return 0;
	}


	@Override
	public short getShort(final String columnLabel) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return 0;
	}



	@Override
	public float getFloat(final String columnLabel) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return 0;
	}


	@Override
	public double getDouble(final String columnLabel) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return 0;
	}


	@Override
	public BigDecimal getBigDecimal(final String columnLabel, final int scale) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return null;
	}


	@Override
	public byte[] getBytes(final String columnLabel) throws SQLException
	{
		LOG.info("columnLabel = " + columnLabel);

		final byte[] result = (byte[])this.map.get(columnLabel);

		if (result == null)
		{
			throw new RuntimeException("field not specified for columnLabel = " + columnLabel);
		}

		return result;
	}



	@Override
	public Time getTime(final String columnLabel) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return null;
	}



	@Override
	public InputStream getAsciiStream(final String columnLabel) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return null;
	}


	@Override
	public InputStream getUnicodeStream(final String columnLabel) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return null;
	}


	@Override
	public InputStream getBinaryStream(final String columnLabel) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return null;
	}


	@Override
	public SQLWarning getWarnings() throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return null;
	}


	@Override
	public void clearWarnings() throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public String getCursorName() throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return null;
	}


	@Override
	public ResultSetMetaData getMetaData() throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return null;
	}


	@Override
	public Object getObject(final int columnIndex) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return null;
	}


	@Override
	public Object getObject(final String columnLabel) throws SQLException
	{
		return this.map.get(columnLabel);
	}


	@Override
	public int findColumn(final String columnLabel) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return 0;
	}


	@Override
	public Reader getCharacterStream(final int columnIndex) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return null;
	}


	@Override
	public Reader getCharacterStream(final String columnLabel) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return null;
	}


	@Override
	public BigDecimal getBigDecimal(final int columnIndex) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return null;
	}


	@Override
	public BigDecimal getBigDecimal(final String columnLabel) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return null;
	}


	@Override
	public boolean isBeforeFirst() throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return false;
	}


	@Override
	public boolean isAfterLast() throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return false;
	}


	@Override
	public boolean isFirst() throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return false;
	}


	@Override
	public boolean isLast() throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return false;
	}


	@Override
	public void beforeFirst() throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void afterLast() throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public boolean first() throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return false;
	}


	@Override
	public boolean last() throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return false;
	}


	@Override
	public int getRow() throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return 0;
	}


	@Override
	public boolean absolute(final int row) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return false;
	}


	@Override
	public boolean relative(final int rows) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return false;
	}


	@Override
	public boolean previous() throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return false;
	}


	@Override
	public void setFetchDirection(final int direction) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public int getFetchDirection() throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return 0;
	}


	@Override
	public void setFetchSize(final int rows) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public int getFetchSize() throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return 0;
	}


	@Override
	public int getType() throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return 0;
	}


	@Override
	public int getConcurrency() throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return 0;
	}


	@Override
	public boolean rowUpdated() throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return false;
	}


	@Override
	public boolean rowInserted() throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return false;
	}


	@Override
	public boolean rowDeleted() throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return false;
	}


	@Override
	public void updateNull(final int columnIndex) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateBoolean(final int columnIndex, final boolean x) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateByte(final int columnIndex, final byte x) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateShort(final int columnIndex, final short x) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateInt(final int columnIndex, final int x) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateLong(final int columnIndex, final long x) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateFloat(final int columnIndex, final float x) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateDouble(final int columnIndex, final double x) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateBigDecimal(final int columnIndex, final BigDecimal x) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateString(final int columnIndex, final String x) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateBytes(final int columnIndex, final byte[] x) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateDate(final int columnIndex, final Date x) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateTime(final int columnIndex, final Time x) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateTimestamp(final int columnIndex, final Timestamp x) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateAsciiStream(final int columnIndex, final InputStream x, final int length) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateBinaryStream(final int columnIndex, final InputStream x, final int length) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateCharacterStream(final int columnIndex, final Reader x, final int length) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateObject(final int columnIndex, final Object x, final int scaleOrLength) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateObject(final int columnIndex, final Object x) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateNull(final String columnLabel) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateBoolean(final String columnLabel, final boolean x) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateByte(final String columnLabel, final byte x) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateShort(final String columnLabel, final short x) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateInt(final String columnLabel, final int x) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateLong(final String columnLabel, final long x) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateFloat(final String columnLabel, final float x) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateDouble(final String columnLabel, final double x) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateBigDecimal(final String columnLabel, final BigDecimal x) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateString(final String columnLabel, final String x) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateBytes(final String columnLabel, final byte[] x) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateDate(final String columnLabel, final Date x) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateTime(final String columnLabel, final Time x) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateTimestamp(final String columnLabel, final Timestamp x) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateAsciiStream(final String columnLabel, final InputStream x, final int length) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateBinaryStream(final String columnLabel, final InputStream x, final int length) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateCharacterStream(final String columnLabel, final Reader reader, final int length)
			throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateObject(final String columnLabel, final Object x, final int scaleOrLength) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateObject(final String columnLabel, final Object x) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void insertRow() throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateRow() throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void deleteRow() throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void refreshRow() throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void cancelRowUpdates() throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void moveToInsertRow() throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void moveToCurrentRow() throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public Statement getStatement() throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return null;
	}


	@Override
	public Object getObject(final int columnIndex, final Map<String, Class<?>> map) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return null;
	}


	@Override
	public Ref getRef(final int columnIndex) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return null;
	}


	@Override
	public Blob getBlob(final int columnIndex) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return null;
	}


	@Override
	public Clob getClob(final int columnIndex) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return null;
	}


	@Override
	public Array getArray(final int columnIndex) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return null;
	}


	@Override
	public Object getObject(final String columnLabel, final Map<String, Class<?>> map) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return null;
	}


	@Override
	public Ref getRef(final String columnLabel) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return null;
	}


	@Override
	public Blob getBlob(final String columnLabel) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return null;
	}


	@Override
	public Clob getClob(final String columnLabel) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return null;
	}


	@Override
	public Array getArray(final String columnLabel) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return null;
	}


	@Override
	public Date getDate(final int columnIndex, final Calendar cal) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return null;
	}


	@Override
	public Date getDate(final String columnLabel, final Calendar cal) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return null;
	}


	@Override
	public Time getTime(final int columnIndex, final Calendar cal) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return null;
	}


	@Override
	public Time getTime(final String columnLabel, final Calendar cal) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return null;
	}


	@Override
	public Timestamp getTimestamp(final int columnIndex, final Calendar cal) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return null;
	}


	@Override
	public Timestamp getTimestamp(final String columnLabel, final Calendar cal) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return null;
	}


	@Override
	public URL getURL(final int columnIndex) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return null;
	}


	@Override
	public URL getURL(final String columnLabel) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return null;
	}


	@Override
	public void updateRef(final int columnIndex, final Ref x) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateRef(final String columnLabel, final Ref x) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateBlob(final int columnIndex, final Blob x) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateBlob(final String columnLabel, final Blob x) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateClob(final int columnIndex, final Clob x) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateClob(final String columnLabel, final Clob x) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateArray(final int columnIndex, final Array x) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateArray(final String columnLabel, final Array x) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public RowId getRowId(final int columnIndex) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return null;
	}


	@Override
	public RowId getRowId(final String columnLabel) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return null;
	}


	@Override
	public void updateRowId(final int columnIndex, final RowId x) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateRowId(final String columnLabel, final RowId x) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public int getHoldability() throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return 0;
	}


	@Override
	public boolean isClosed() throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return false;
	}


	@Override
	public void updateNString(final int columnIndex, final String nString) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateNString(final String columnLabel, final String nString) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateNClob(final int columnIndex, final NClob nClob) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateNClob(final String columnLabel, final NClob nClob) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public NClob getNClob(final int columnIndex) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return null;
	}


	@Override
	public NClob getNClob(final String columnLabel) throws SQLException
	{
		throw new RuntimeException("METHOD NOT IMPLEMENTED");
	}


	@Override
	public SQLXML getSQLXML(final int columnIndex) throws SQLException
	{
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
	}


	@Override
	public SQLXML getSQLXML(final String columnLabel) throws SQLException
	{
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
	}


	@Override
	public void updateSQLXML(final int columnIndex, final SQLXML xmlObject) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateSQLXML(final String columnLabel, final SQLXML xmlObject) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public String getNString(final int columnIndex) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return null;
	}


	@Override
	public String getNString(final String columnLabel) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return null;
	}


	@Override
	public Reader getNCharacterStream(final int columnIndex) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return null;
	}


	@Override
	public Reader getNCharacterStream(final String columnLabel) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return null;
	}


	@Override
	public void updateNCharacterStream(final int columnIndex, final Reader x, final long length) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateNCharacterStream(final String columnLabel, final Reader reader, final long length)
			throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateAsciiStream(final int columnIndex, final InputStream x, final long length) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateBinaryStream(final int columnIndex, final InputStream x, final long length) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateCharacterStream(final int columnIndex, final Reader x, final long length) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateAsciiStream(final String columnLabel, final InputStream x, final long length) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateBinaryStream(final String columnLabel, final InputStream x, final long length)
			throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateCharacterStream(final String columnLabel, final Reader reader, final long length)
			throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateBlob(final int columnIndex, final InputStream inputStream, final long length) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateBlob(final String columnLabel, final InputStream inputStream, final long length)
			throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateClob(final int columnIndex, final Reader reader, final long length) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateClob(final String columnLabel, final Reader reader, final long length) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateNClob(final int columnIndex, final Reader reader, final long length) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateNClob(final String columnLabel, final Reader reader, final long length) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateNCharacterStream(final int columnIndex, final Reader x) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateNCharacterStream(final String columnLabel, final Reader reader) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateAsciiStream(final int columnIndex, final InputStream x) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateBinaryStream(final int columnIndex, final InputStream x) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateCharacterStream(final int columnIndex, final Reader x) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateAsciiStream(final String columnLabel, final InputStream x) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateBinaryStream(final String columnLabel, final InputStream x) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateCharacterStream(final String columnLabel, final Reader reader) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateBlob(final int columnIndex, final InputStream inputStream) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateBlob(final String columnLabel, final InputStream inputStream) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateClob(final int columnIndex, final Reader reader) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateClob(final String columnLabel, final Reader reader) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateNClob(final int columnIndex, final Reader reader) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public void updateNClob(final String columnLabel, final Reader reader) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}

	}


	@Override
	public <T> T getObject(final int columnIndex, final Class<T> type) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return null;
	}


	@Override
	public <T> T getObject(final String columnLabel, final Class<T> type) throws SQLException
	{
		if (true)
		{
			throw new RuntimeException("METHOD NOT IMPLEMENTED");
		}
		return null;
	}



}
