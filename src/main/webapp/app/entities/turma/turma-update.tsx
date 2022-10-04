import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPessoa } from 'app/shared/model/pessoa.model';
import { getEntities as getPessoas } from 'app/entities/pessoa/pessoa.reducer';
import { ITurma } from 'app/shared/model/turma.model';
import { SexoTurma } from 'app/shared/model/enumerations/sexo-turma.model';
import { FaixaEtaria } from 'app/shared/model/enumerations/faixa-etaria.model';
import { getEntity, updateEntity, createEntity, reset } from './turma.reducer';

export const TurmaUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const pessoas = useAppSelector(state => state.pessoa.entities);
  const turmaEntity = useAppSelector(state => state.turma.entity);
  const loading = useAppSelector(state => state.turma.loading);
  const updating = useAppSelector(state => state.turma.updating);
  const updateSuccess = useAppSelector(state => state.turma.updateSuccess);
  const sexoTurmaValues = Object.keys(SexoTurma);
  const faixaEtariaValues = Object.keys(FaixaEtaria);

  const handleClose = () => {
    navigate('/turma' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getPessoas({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.dataCadastro = convertDateTimeToServer(values.dataCadastro);

    const entity = {
      ...turmaEntity,
      ...values,
      professor: pessoas.find(it => it.id.toString() === values.professor.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          dataCadastro: displayDefaultDateTime(),
        }
      : {
          sexoTurma: 'MASCULINO',
          faixaEtaria: 'CRIANCA',
          ...turmaEntity,
          dataCadastro: convertDateTimeFromServer(turmaEntity.dataCadastro),
          professor: turmaEntity?.professor?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="escolaEbdApp.turma.home.createOrEditLabel" data-cy="TurmaCreateUpdateHeading">
            <Translate contentKey="escolaEbdApp.turma.home.createOrEditLabel">Create or edit a Turma</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="turma-id"
                  label={translate('escolaEbdApp.turma.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('escolaEbdApp.turma.nome')}
                id="turma-nome"
                name="nome"
                data-cy="nome"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('escolaEbdApp.turma.sexoTurma')}
                id="turma-sexoTurma"
                name="sexoTurma"
                data-cy="sexoTurma"
                type="select"
              >
                {sexoTurmaValues.map(sexoTurma => (
                  <option value={sexoTurma} key={sexoTurma}>
                    {translate('escolaEbdApp.SexoTurma.' + sexoTurma)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('escolaEbdApp.turma.faixaEtaria')}
                id="turma-faixaEtaria"
                name="faixaEtaria"
                data-cy="faixaEtaria"
                type="select"
              >
                {faixaEtariaValues.map(faixaEtaria => (
                  <option value={faixaEtaria} key={faixaEtaria}>
                    {translate('escolaEbdApp.FaixaEtaria.' + faixaEtaria)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('escolaEbdApp.turma.dataInicio')}
                id="turma-dataInicio"
                name="dataInicio"
                data-cy="dataInicio"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('escolaEbdApp.turma.dataFim')}
                id="turma-dataFim"
                name="dataFim"
                data-cy="dataFim"
                type="date"
              />
              <ValidatedField
                label={translate('escolaEbdApp.turma.dataCadastro')}
                id="turma-dataCadastro"
                name="dataCadastro"
                data-cy="dataCadastro"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="turma-professor"
                name="professor"
                data-cy="professor"
                label={translate('escolaEbdApp.turma.professor')}
                type="select"
              >
                <option value="" key="0" />
                {pessoas
                  ? pessoas.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/turma" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default TurmaUpdate;
