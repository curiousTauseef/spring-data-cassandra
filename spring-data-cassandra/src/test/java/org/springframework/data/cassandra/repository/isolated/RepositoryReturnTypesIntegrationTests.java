/*
 * Copyright 2016-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.data.cassandra.repository.isolated;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.domain.AllPossibleTypes;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.data.cassandra.test.integration.support.AbstractSpringDataEmbeddedCassandraIntegrationTest;
import org.springframework.data.cassandra.test.integration.support.IntegrationTestConfig;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.datastax.driver.core.LocalDate;

/**
 * Integration tests for various return types on a Cassandra repository.
 *
 * @author Mark Paluch
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@SuppressWarnings("Since15")
public class RepositoryReturnTypesIntegrationTests extends AbstractSpringDataEmbeddedCassandraIntegrationTest {

	@Configuration
	@EnableCassandraRepositories(basePackageClasses = RepositoryReturnTypesIntegrationTests.class,
			considerNestedRepositories = true)
	public static class Config extends IntegrationTestConfig {

		@Override
		public String[] getEntityBasePackages() {
			return new String[] { AllPossibleTypes.class.getPackage().getName() };
		}

		@Override
		public SchemaAction getSchemaAction() {
			return SchemaAction.RECREATE_DROP_UNUSED;
		}
	}

	@Autowired AllPossibleTypesRepository allPossibleTypesRepository;

	@Before
	public void setUp() throws Exception {
		allPossibleTypesRepository.deleteAll();
	}

	@Test // DATACASS-271
	public void shouldReturnOptional() {

		AllPossibleTypes entity = new AllPossibleTypes("123");
		allPossibleTypesRepository.save(entity);

		Optional<AllPossibleTypes> result = allPossibleTypesRepository.findOptionalById(entity.getId());
		assertThat(result.isPresent()).isTrue();
		assertThat(result.get()).isInstanceOf(AllPossibleTypes.class);
	}

	@Test // DATACASS-271
	public void shouldReturnList() {

		AllPossibleTypes entity = new AllPossibleTypes("123");
		allPossibleTypesRepository.save(entity);

		List<AllPossibleTypes> result = allPossibleTypesRepository.findManyById(entity.getId());
		assertThat(result.isEmpty()).isFalse();
		assertThat(result).contains(entity);
	}

	@Test // DATACASS-271
	public void shouldReturnInetAddress() throws UnknownHostException {

		AllPossibleTypes entity = new AllPossibleTypes("123");
		entity.setInet(InetAddress.getByName("localhost"));
		allPossibleTypesRepository.save(entity);

		InetAddress result = allPossibleTypesRepository.findInetAddressById(entity.getId());
		assertThat(result).isEqualTo(entity.getInet());
	}

	@Test // DATACASS-271
	public void shouldReturnOptionalInetAddress() throws UnknownHostException {

		AllPossibleTypes entity = new AllPossibleTypes("123");
		entity.setInet(InetAddress.getByName("localhost"));
		allPossibleTypesRepository.save(entity);

		Optional<InetAddress> result = allPossibleTypesRepository.findOptionalInetById(entity.getId());
		assertThat(result.isPresent()).isTrue();
		assertThat(result.get()).isEqualTo(entity.getInet());
	}

	@Test // DATACASS-271
	public void shouldReturnBoxedByte() {

		AllPossibleTypes entity = new AllPossibleTypes("123");
		entity.setBoxedByte(Byte.valueOf("1"));
		allPossibleTypesRepository.save(entity);

		Byte result = allPossibleTypesRepository.findBoxedByteById(entity.getId());
		assertThat(result).isEqualTo(entity.getBoxedByte());
	}

	@Test // DATACASS-271
	public void shouldReturnPrimitiveByte() {

		AllPossibleTypes entity = new AllPossibleTypes("123");
		entity.setPrimitiveByte(Byte.MAX_VALUE);
		allPossibleTypesRepository.save(entity);

		byte result = allPossibleTypesRepository.findPrimitiveByteById(entity.getId());
		assertThat(result).isEqualTo(entity.getPrimitiveByte());
	}

	@Test // DATACASS-271
	public void shouldReturnBoxedShort() {

		AllPossibleTypes entity = new AllPossibleTypes("123");
		entity.setBoxedShort(Short.MAX_VALUE);
		allPossibleTypesRepository.save(entity);

		Short result = allPossibleTypesRepository.findBoxedShortById(entity.getId());
		assertThat(result).isEqualTo(entity.getBoxedShort());
	}

	@Test // DATACASS-271
	public void shouldReturnBoxedLong() {

		AllPossibleTypes entity = new AllPossibleTypes("123");
		entity.setBoxedLong(Long.MAX_VALUE);
		allPossibleTypesRepository.save(entity);

		Long result = allPossibleTypesRepository.findBoxedLongById(entity.getId());
		assertThat(result).isEqualTo(entity.getBoxedLong());
	}

	@Test // DATACASS-271
	public void shouldReturnBoxedInteger() {

		AllPossibleTypes entity = new AllPossibleTypes("123");
		entity.setBoxedInteger(Integer.MAX_VALUE);
		allPossibleTypesRepository.save(entity);

		Integer result = allPossibleTypesRepository.findBoxedIntegerById(entity.getId());
		assertThat(result).isEqualTo(entity.getBoxedInteger());
	}

	@Test // DATACASS-271
	public void shouldReturnBoxedDouble() {

		AllPossibleTypes entity = new AllPossibleTypes("123");
		entity.setBoxedDouble(Double.MAX_VALUE);
		allPossibleTypesRepository.save(entity);

		Double result = allPossibleTypesRepository.findBoxedDoubleById(entity.getId());
		assertThat(result).isEqualTo(entity.getBoxedDouble());
	}

	@Test // DATACASS-271
	public void shouldReturnBoxedDoubleFromInteger() {

		AllPossibleTypes entity = new AllPossibleTypes("123");
		entity.setBoxedInteger(Integer.MAX_VALUE);
		allPossibleTypesRepository.save(entity);

		Double result = allPossibleTypesRepository.findDoubleFromIntegerById(entity.getId());
		assertThat(result).isCloseTo(entity.getBoxedInteger(), offset(0.01d));
	}

	@Test // DATACASS-271
	public void shouldReturnBoxedBoolean() {

		AllPossibleTypes entity = new AllPossibleTypes("123");
		entity.setBoxedBoolean(true);
		allPossibleTypesRepository.save(entity);

		Boolean result = allPossibleTypesRepository.findBoxedBooleanById(entity.getId());
		assertThat(result).isEqualTo(entity.getBoxedBoolean());
	}

	@Test // DATACASS-271
	public void shouldReturnDate() {

		AllPossibleTypes entity = new AllPossibleTypes("123");
		entity.setDate(LocalDate.fromDaysSinceEpoch(1));
		allPossibleTypesRepository.save(entity);

		LocalDate result = allPossibleTypesRepository.findLocalDateById(entity.getId());
		assertThat(result).isEqualTo(entity.getDate());
	}

	@Test // DATACASS-271
	public void shouldReturnTimestamp() {

		AllPossibleTypes entity = new AllPossibleTypes("123");
		entity.setTimestamp(new Date(1));
		allPossibleTypesRepository.save(entity);

		Date result = allPossibleTypesRepository.findTimestampById(entity.getId());
		assertThat(result).isEqualTo(entity.getTimestamp());
	}

	@Test // DATACASS-271
	public void shouldReturnBigDecimal() {

		AllPossibleTypes entity = new AllPossibleTypes("123");
		entity.setBigDecimal(BigDecimal.ONE);
		allPossibleTypesRepository.save(entity);

		BigDecimal result = allPossibleTypesRepository.findBigDecimalById(entity.getId());
		assertThat(result).isEqualTo(entity.getBigDecimal());
	}

	@Test // DATACASS-271
	public void shouldReturnBigInteger() {

		AllPossibleTypes entity = new AllPossibleTypes("123");
		entity.setBigInteger(BigInteger.ONE);
		allPossibleTypesRepository.save(entity);

		BigInteger result = allPossibleTypesRepository.findBigIntegerById(entity.getId());
		assertThat(result).isEqualTo(entity.getBigInteger());
	}

	@Test // DATACASS-271
	public void shouldReturnEntityAsMap() {

		AllPossibleTypes entity = new AllPossibleTypes("123");
		entity.setPrimitiveInteger(123);
		entity.setBigInteger(BigInteger.ONE);
		allPossibleTypesRepository.save(entity);

		Map<String, Object> result = allPossibleTypesRepository.findEntityAsMapById(entity.getId());
		assertThat(result).hasSize(43);
		assertThat(result.get("primitiveinteger")).isEqualTo((Object) Integer.valueOf(123));
		assertThat(result.get("biginteger")).isEqualTo((Object) BigInteger.ONE);
	}

	public interface AllPossibleTypesRepository extends CrudRepository<AllPossibleTypes, String> {

		// blob/byte-buffer result do not work yet.

		// returning a map field does not work yet.

		@Query("select * from allpossibletypes where id = ?0")
		Optional<AllPossibleTypes> findOptionalById(String id);

		@Query("select * from allpossibletypes where id = ?0")
		List<AllPossibleTypes> findManyById(String id);

		@Query("select inet from allpossibletypes where id = ?0")
		InetAddress findInetAddressById(String id);

		@Query("select inet from allpossibletypes where id = ?0")
		Optional<InetAddress> findOptionalInetById(String id);

		@Query("select boxedByte from allpossibletypes where id = ?0")
		Byte findBoxedByteById(String id);

		@Query("select primitiveByte from allpossibletypes where id = ?0")
		byte findPrimitiveByteById(String id);

		@Query("select boxedShort from allpossibletypes where id = ?0")
		Short findBoxedShortById(String id);

		@Query("select boxedLong from allpossibletypes where id = ?0")
		Long findBoxedLongById(String id);

		@Query("select boxedInteger from allpossibletypes where id = ?0")
		Integer findBoxedIntegerById(String id);

		@Query("select boxedInteger from allpossibletypes where id = ?0")
		Double findDoubleFromIntegerById(String id);

		@Query("select boxedDouble from allpossibletypes where id = ?0")
		Double findBoxedDoubleById(String id);

		@Query("select boxedBoolean from allpossibletypes where id = ?0")
		Boolean findBoxedBooleanById(String id);

		@Query("select date from allpossibletypes where id = ?0")
		LocalDate findLocalDateById(String id);

		@Query("select timestamp from allpossibletypes where id = ?0")
		Date findTimestampById(String id);

		@Query("select bigDecimal from allpossibletypes where id = ?0")
		BigDecimal findBigDecimalById(String id);

		@Query("select bigInteger from allpossibletypes where id = ?0")
		BigInteger findBigIntegerById(String id);

		@Query("select * from allpossibletypes where id = ?0")
		Map<String, Object> findEntityAsMapById(String id);
	}
}
