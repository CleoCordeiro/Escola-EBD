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
import { ITurma } from 'app/shared/model/turma.model';
import { getEntities as getTurmas } from 'app/entities/turma/turma.reducer';
import { IResponsavel } from 'app/shared/model/responsavel.model';
import { getEntities as getResponsavels } from 'app/entities/responsavel/responsavel.reducer';
import { IPessoa } from 'app/shared/model/pessoa.model';
import { Sexo } from 'app/shared/model/enumerations/sexo.model';
import { TipoPessoa } from 'app/shared/model/enumerations/tipo-pessoa.model';
import { getEntity, updateEntity, createEntity, reset } from './pessoa.reducer';

export const PessoaUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const enderecos = useAppSelector(state => state.endereco.entities);
  const turmas = useAppSelector(state => state.turma.entities);
  const responsavels = useAppSelector(state => state.responsavel.entities);
  const pessoaEntity = useAppSelector(state => state.pessoa.entity);
  const loading = useAppSelector(state => state.pessoa.loading);
  const updating = useAppSelector(state => state.pessoa.updating);
  const updateSuccess = useAppSelector(state => state.pessoa.updateSuccess);
  const sexoValues = Object.keys(Sexo);
  const tipoPessoaValues = Object.keys(TipoPessoa);

  const handleClose = () => {
    navigate('/pessoa' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getEnderecos({}));
    dispatch(getTurmas({}));
    dispatch(getResponsavels({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.dataCadastro = convertDateTimeToServer(values.dataCadastro);
    values.dataAtualizacao = convertDateTimeToServer(values.dataAtualizacao);

    const entity = {
      ...pessoaEntity,
      ...values,
      enderecos: mapIdList(values.enderecos),
      turma: turmas.find(it => it.id.toString() === values.turma.toString()),
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
          dataAtualizacao: displayDefaultDateTime(),
        }
      : {
          sexo: 'MASCULINO',
          tipoPessoa: 'ALUNO',
          ...pessoaEntity,
          dataCadastro: convertDateTimeFromServer(pessoaEntity.dataCadastro),
          dataAtualizacao: convertDateTimeFromServer(pessoaEntity.dataAtualizacao),
          enderecos: pessoaEntity?.enderecos?.map(e => e.id.toString()),
          turma: pessoaEntity?.turma?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="escolaEbdApp.pessoa.home.createOrEditLabel" data-cy="PessoaCreateUpdateHeading">
            <Translate contentKey="escolaEbdApp.pessoa.home.createOrEditLabel">Create or edit a Pessoa</Translate>
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
                  id="pessoa-id"
                  label={translate('escolaEbdApp.pessoa.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('escolaEbdApp.pessoa.nome')}
                id="pessoa-nome"
                name="nome"
                data-cy="nome"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('escolaEbdApp.pessoa.dataNascimento')}
                id="pessoa-dataNascimento"
                name="dataNascimento"
                data-cy="dataNascimento"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('escolaEbdApp.pessoa.cpf')}
                id="pessoa-cpf"
                name="cpf"
                data-cy="cpf"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField label={translate('escolaEbdApp.pessoa.sexo')} id="pessoa-sexo" name="sexo" data-cy="sexo" type="select">
                {sexoValues.map(sexo => (
                  <option value={sexo} key={sexo}>
                    {translate('escolaEbdApp.Sexo.' + sexo)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('escolaEbdApp.pessoa.tipoPessoa')}
                id="pessoa-tipoPessoa"
                name="tipoPessoa"
                data-cy="tipoPessoa"
                type="select"
              >
                {tipoPessoaValues.map(tipoPessoa => (
                  <option value={tipoPessoa} key={tipoPessoa}>
                    {translate('escolaEbdApp.TipoPessoa.' + tipoPessoa)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('escolaEbdApp.pessoa.dataCadastro')}
                id="pessoa-dataCadastro"
                name="dataCadastro"
                data-cy="dataCadastro"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('escolaEbdApp.pessoa.dataAtualizacao')}
                id="pessoa-dataAtualizacao"
                name="dataAtualizacao"
                data-cy="dataAtualizacao"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('escolaEbdApp.pessoa.enderecos')}
                id="pessoa-enderecos"
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
              <ValidatedField id="pessoa-turma" name="turma" data-cy="turma" label={translate('escolaEbdApp.pessoa.turma')} type="select">
                <option value="" key="0" />
                {turmas
                  ? turmas.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/pessoa" replace color="info">
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

export default PessoaUpdate;
