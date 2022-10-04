import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEndereco } from 'app/shared/model/endereco.model';
import { getEntities as getEnderecos } from 'app/entities/endereco/endereco.reducer';
import { IPessoa } from 'app/shared/model/pessoa.model';
import { getEntities as getPessoas } from 'app/entities/pessoa/pessoa.reducer';
import { IResponsavel } from 'app/shared/model/responsavel.model';
import { Sexo } from 'app/shared/model/enumerations/sexo.model';
import { getEntity, updateEntity, createEntity, reset } from './responsavel.reducer';

export const ResponsavelUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const enderecos = useAppSelector(state => state.endereco.entities);
  const pessoas = useAppSelector(state => state.pessoa.entities);
  const responsavelEntity = useAppSelector(state => state.responsavel.entity);
  const loading = useAppSelector(state => state.responsavel.loading);
  const updating = useAppSelector(state => state.responsavel.updating);
  const updateSuccess = useAppSelector(state => state.responsavel.updateSuccess);
  const sexoValues = Object.keys(Sexo);

  const handleClose = () => {
    navigate('/responsavel' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getEnderecos({}));
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
      ...responsavelEntity,
      ...values,
      enderecos: mapIdList(values.enderecos),
      alunos: mapIdList(values.alunos),
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
          sexo: 'MASCULINO',
          ...responsavelEntity,
          dataCadastro: convertDateTimeFromServer(responsavelEntity.dataCadastro),
          enderecos: responsavelEntity?.enderecos?.map(e => e.id.toString()),
          alunos: responsavelEntity?.alunos?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="escolaEbdApp.responsavel.home.createOrEditLabel" data-cy="ResponsavelCreateUpdateHeading">
            <Translate contentKey="escolaEbdApp.responsavel.home.createOrEditLabel">Create or edit a Responsavel</Translate>
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
                  id="responsavel-id"
                  label={translate('escolaEbdApp.responsavel.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('escolaEbdApp.responsavel.nome')}
                id="responsavel-nome"
                name="nome"
                data-cy="nome"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('escolaEbdApp.responsavel.cpf')}
                id="responsavel-cpf"
                name="cpf"
                data-cy="cpf"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('escolaEbdApp.responsavel.sexo')}
                id="responsavel-sexo"
                name="sexo"
                data-cy="sexo"
                type="select"
              >
                {sexoValues.map(sexo => (
                  <option value={sexo} key={sexo}>
                    {translate('escolaEbdApp.Sexo.' + sexo)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('escolaEbdApp.responsavel.parentesco')}
                id="responsavel-parentesco"
                name="parentesco"
                data-cy="parentesco"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('escolaEbdApp.responsavel.dataCadastro')}
                id="responsavel-dataCadastro"
                name="dataCadastro"
                data-cy="dataCadastro"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('escolaEbdApp.responsavel.enderecos')}
                id="responsavel-enderecos"
                data-cy="enderecos"
                type="select"
                multiple
                name="enderecos"
              >
                <option value="" key="0" />
                {enderecos
                  ? enderecos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label={translate('escolaEbdApp.responsavel.alunos')}
                id="responsavel-alunos"
                data-cy="alunos"
                type="select"
                multiple
                name="alunos"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/responsavel" replace color="info">
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

export default ResponsavelUpdate;
