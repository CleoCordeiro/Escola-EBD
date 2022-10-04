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
import { IResponsavel } from 'app/shared/model/responsavel.model';
import { getEntities as getResponsavels } from 'app/entities/responsavel/responsavel.reducer';
import { ITelefone } from 'app/shared/model/telefone.model';
import { getEntity, updateEntity, createEntity, reset } from './telefone.reducer';

export const TelefoneUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const pessoas = useAppSelector(state => state.pessoa.entities);
  const responsavels = useAppSelector(state => state.responsavel.entities);
  const telefoneEntity = useAppSelector(state => state.telefone.entity);
  const loading = useAppSelector(state => state.telefone.loading);
  const updating = useAppSelector(state => state.telefone.updating);
  const updateSuccess = useAppSelector(state => state.telefone.updateSuccess);

  const handleClose = () => {
    navigate('/telefone' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getPessoas({}));
    dispatch(getResponsavels({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...telefoneEntity,
      ...values,
      pessoa: pessoas.find(it => it.id.toString() === values.pessoa.toString()),
      responsavel: responsavels.find(it => it.id.toString() === values.responsavel.toString()),
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
          ...telefoneEntity,
          pessoa: telefoneEntity?.pessoa?.id,
          responsavel: telefoneEntity?.responsavel?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="escolaEbdApp.telefone.home.createOrEditLabel" data-cy="TelefoneCreateUpdateHeading">
            <Translate contentKey="escolaEbdApp.telefone.home.createOrEditLabel">Create or edit a Telefone</Translate>
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
                  id="telefone-id"
                  label={translate('escolaEbdApp.telefone.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('escolaEbdApp.telefone.numero')}
                id="telefone-numero"
                name="numero"
                data-cy="numero"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('escolaEbdApp.telefone.whatsapp')}
                id="telefone-whatsapp"
                name="whatsapp"
                data-cy="whatsapp"
                check
                type="checkbox"
              />
              <ValidatedField
                id="telefone-pessoa"
                name="pessoa"
                data-cy="pessoa"
                label={translate('escolaEbdApp.telefone.pessoa')}
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
              <ValidatedField
                id="telefone-responsavel"
                name="responsavel"
                data-cy="responsavel"
                label={translate('escolaEbdApp.telefone.responsavel')}
                type="select"
              >
                <option value="" key="0" />
                {responsavels
                  ? responsavels.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/telefone" replace color="info">
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

export default TelefoneUpdate;
