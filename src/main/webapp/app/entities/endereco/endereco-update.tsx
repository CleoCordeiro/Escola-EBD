import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IResponsavel } from 'app/shared/model/responsavel.model';
import { getEntities as getResponsavels } from 'app/entities/responsavel/responsavel.reducer';
import { IPessoa } from 'app/shared/model/pessoa.model';
import { getEntities as getPessoas } from 'app/entities/pessoa/pessoa.reducer';
import { IEndereco } from 'app/shared/model/endereco.model';
import { getEntity, updateEntity, createEntity, reset } from './endereco.reducer';

export const EnderecoUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const responsavels = useAppSelector(state => state.responsavel.entities);
  const pessoas = useAppSelector(state => state.pessoa.entities);
  const enderecoEntity = useAppSelector(state => state.endereco.entity);
  const loading = useAppSelector(state => state.endereco.loading);
  const updating = useAppSelector(state => state.endereco.updating);
  const updateSuccess = useAppSelector(state => state.endereco.updateSuccess);

  const handleClose = () => {
    navigate('/endereco' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getResponsavels({}));
    dispatch(getPessoas({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...enderecoEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...enderecoEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="escolaEbdApp.endereco.home.createOrEditLabel" data-cy="EnderecoCreateUpdateHeading">
            <Translate contentKey="escolaEbdApp.endereco.home.createOrEditLabel">Create or edit a Endereco</Translate>
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
                  id="endereco-id"
                  label={translate('escolaEbdApp.endereco.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('escolaEbdApp.endereco.logradouro')}
                id="endereco-logradouro"
                name="logradouro"
                data-cy="logradouro"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('escolaEbdApp.endereco.numero')}
                id="endereco-numero"
                name="numero"
                data-cy="numero"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('escolaEbdApp.endereco.complemento')}
                id="endereco-complemento"
                name="complemento"
                data-cy="complemento"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('escolaEbdApp.endereco.bairro')}
                id="endereco-bairro"
                name="bairro"
                data-cy="bairro"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('escolaEbdApp.endereco.cidade')}
                id="endereco-cidade"
                name="cidade"
                data-cy="cidade"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('escolaEbdApp.endereco.estado')}
                id="endereco-estado"
                name="estado"
                data-cy="estado"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('escolaEbdApp.endereco.cep')}
                id="endereco-cep"
                name="cep"
                data-cy="cep"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/endereco" replace color="info">
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

export default EnderecoUpdate;
